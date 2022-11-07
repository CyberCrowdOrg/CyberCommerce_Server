package org.cybercrowd.mvp.service.impl;

import com.alibaba.fastjson2.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.cybercrowd.mvp.dto.BaseOrderDto;
import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.UserWalletService;
import org.cybercrowd.mvp.dto.request.*;
import org.cybercrowd.mvp.dto.response.*;
import org.cybercrowd.mvp.enums.*;
import org.cybercrowd.mvp.mapper.*;
import org.cybercrowd.mvp.model.*;
import org.cybercrowd.mvp.service.DaoService;
import org.cybercrowd.mvp.service.OrderService;
import org.cybercrowd.mvp.util.IDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    TaskInfoMapper taskInfoMapper;
    @Autowired
    TaskEventMapper taskEventMapper;
    @Autowired
    TaskRulesMapper taskRulesMapper;
    @Autowired
    GoodsInfoMapper goodsInfoMapper;
    @Autowired
    BaseCoinMapper baseCoinMapper;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    UserWalletInfoMapper userWalletInfoMapper;
    @Autowired
    OrderInfoMapper orderInfoMapper;
    @Autowired
    UserWalletService userWalletService;
    @Autowired
    CommonService commonService;
    @Autowired
    DaoService daoService;

    @Override
    public BaseResponse grouponOrder(GrouponOrderReq grouponOrderReq) {
        BaseResponse baseResponse = new BaseResponse();
        String taskId = grouponOrderReq.getTaskId();
        Long userId = grouponOrderReq.getUserId();
        //查询任务是否存在
        TaskInfo taskInfo = taskInfoMapper.selectTaskByTaskId(taskId);
        if(null == taskInfo || !TaskTypeEnum.GROUPON.getCode().equals(taskInfo.getTaskType())){
            logger.info("拼团购买订单业务,taskId:{} 未找到响应任务信息",taskId);
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        //查询商品团购价格
        GoodsInfo goodsInfo = goodsInfoMapper.selectByPrimaryKey(taskInfo.getGoodsId());
        //创建交易订单
        OrderInfo orderInfo = createOrder(taskInfo.getTaskId(),goodsInfo.getGoodsSalePrice(),userId, OrderTypeEnum.PAYMENT);
        //检查余额是否足以付款,并扣款
        baseResponse = userWalletService.userWalletBalanceUpdate(userId,orderInfo.getOrderCoinId(),orderInfo.getOrderNo(),
                orderInfo.getOrderType(),orderInfo.getOrderCoinAmount());
        logger.info("拼团购买订单业务,用户扣款结果:{}",JSON.toJSONString(baseResponse));
        if(ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())){
            orderInfo.setOrderStatus(OrderStatusEnum.SUCCESS.getCode());
            orderInfo.setSuccessTime(new Date());
            orderInfo.setUpdateTime(new Date());
            orderInfoMapper.insertSelective(orderInfo);
            //创建事件
            TaskEvent taskEvent = commonService.createTaskEvent(taskInfo, TaskEventEnum.GROUPON_BUY);
            taskEventMapper.insertSelective(taskEvent);
            //任务的商品销量更新s
            taskInfo.setDealQuantity(taskInfo.getDealQuantity()+1);
            taskInfoMapper.updateByPrimaryKeySelective(taskInfo);
            //加入Dao
            daoService.joinDaoOrganize(userId,taskId);
            GrouponOrderRes grouponOrderRes = new GrouponOrderRes();
            grouponOrderRes.setOrderCoinId(orderInfo.getOrderCoinId());
            grouponOrderRes.setOrderCoinName(orderInfo.getOrderCoinName());
            grouponOrderRes.setOrderCoinAmount(orderInfo.getOrderCoinAmount());
            grouponOrderRes.setOrderNo(orderInfo.getOrderNo());
            baseResponse.setData(grouponOrderRes);
        }
        logger.info("拼团购买订单业务,响应结果:{}", JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @Override
    public BaseResponse separateOrder(SeparateOrderReq separateOrderReq) {
        BaseResponse baseResponse = new BaseResponse();
        String taskId = separateOrderReq.getTaskId();
        Long userId = separateOrderReq.getUserId();
        //查询任务是否存在
        TaskInfo taskInfo = taskInfoMapper.selectTaskByTaskId(taskId);
        if(null == taskInfo || !TaskTypeEnum.SALES.getCode().equals(taskInfo.getTaskType())
                ||TaskTypeEnum.DISTRIBUTOR.getCode().equals(taskInfo.getTaskType()) ){
            logger.info("单独购买订单业务,taskId:{} 未找到响应任务信息",taskId);
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        //查询商品
        GoodsInfo goodsInfo = goodsInfoMapper.selectByPrimaryKey(taskInfo.getGoodsId());
        //创建交易订单
        OrderInfo orderInfo = createOrder(taskInfo.getTaskId(),goodsInfo.getGoodsSalePrice(),userId, OrderTypeEnum.PAYMENT);
        //检查余额是否足以付款,并扣款
        baseResponse = userWalletService.userWalletBalanceUpdate(userId,orderInfo.getOrderCoinId(),orderInfo.getOrderNo(),
                orderInfo.getOrderType(),orderInfo.getOrderCoinAmount());
        logger.info("单独购买订单业务,用户扣款结果:{}",JSON.toJSONString(baseResponse));
        if(ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())){
            orderInfo.setOrderStatus(OrderStatusEnum.SUCCESS.getCode());
            orderInfo.setSuccessTime(new Date());
            orderInfo.setUpdateTime(new Date());
            orderInfoMapper.insertSelective(orderInfo);
            //任务的商品销量更新
            taskInfo.setDealQuantity(taskInfo.getDealQuantity()+1);
            taskInfoMapper.updateByPrimaryKeySelective(taskInfo);

            SeparateOrderRes separateOrderRes = new SeparateOrderRes();
            separateOrderRes.setOrderCoinId(orderInfo.getOrderCoinId());
            separateOrderRes.setOrderCoinName(orderInfo.getOrderCoinName());
            separateOrderRes.setOrderCoinAmount(orderInfo.getOrderCoinAmount());
            separateOrderRes.setOrderNo(orderInfo.getOrderNo());
            baseResponse.setData(separateOrderRes);
        }
        logger.info("单独购买订单业务,响应结果:{}", JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @Override
    public BaseResponse rechargeOrder(RechargeOrderReq rechargeOrderReq) {
        BaseResponse baseResponse = new BaseResponse();
        Long userId = rechargeOrderReq.getUserId();
        Long coinId = rechargeOrderReq.getCoinId();
        BigDecimal amount = rechargeOrderReq.getAmount();
        //检查充值的币种是否存在
        BaseCoin baseCoin = baseCoinMapper.selectBaseCoinByCoinId(coinId);
        if(null == baseCoin){
            logger.info("充值订单业务,coinId:{} 未找到对应币种信息,充值失败",coinId);
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.RECHARGE_COIN_NOT_FOUND_ERROR);
            return baseResponse;
        }
        //创建充值订单
        OrderInfo orderInfo = createRechargeOrder(baseCoin,userId,amount);
        //用户钱包余额充值
        baseResponse = userWalletService.userWalletBalanceUpdate(userId,orderInfo.getOrderCoinId(),orderInfo.getOrderNo(),
                orderInfo.getOrderType(),orderInfo.getOrderCoinAmount());
        if(ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())){
            orderInfo.setOrderStatus(OrderStatusEnum.SUCCESS.getCode());
            orderInfo.setUpdateTime(new Date());
            orderInfo.setSuccessTime(new Date());
            orderInfoMapper.insertSelective(orderInfo);

        }
        return baseResponse;
    }

    @Override
    public BaseResponse haggleRewardOrder(HaggleRewardOrderReq haggleRewardOrderReq) {
        BaseResponse baseResponse = new BaseResponse();
        String taskId = haggleRewardOrderReq.getTaskId();
        Long userId = haggleRewardOrderReq.getUserId();
        //查询任务是否存在
        TaskInfo taskInfo = taskInfoMapper.selectTaskByTaskId(taskId);
        if(null == taskInfo || !TaskTypeEnum.GROUPON.getCode().equals(taskInfo.getTaskType())){
            logger.info("砍价奖励订单业务,taskId:{} 未找到响应任务信息",taskId);
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        //验证是否已经参与过砍价了
        TaskEvent taskEvent = taskEventMapper.findUserTaskEvent(userId,taskId,TaskEventEnum.HAGGLE.getCode());
        if(null != taskEvent){
            logger.info("砍价奖励订单业务,已参加过砍价,不能再次参加");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REPEAT_HAGGLING_ERROR);
            return baseResponse;
        }
        //查询砍价规则
        TaskRules taskRules = taskRulesMapper.selectTaskRulesByType(taskInfo.getTaskParentId(), TaskRulesTypeEnum.GROUPON.getCode());
        GrouponRulesDto grouponRulesDto = JSON.parseObject(taskRules.getTaskRulesJson(),GrouponRulesDto.class);
        //砍价奖励金额
        BigDecimal haggleRewardAmount = grouponRulesDto.getHaggleRewardAmount();
        //计算随机砍价金额
        Float haggleMinAmount = grouponRulesDto.getHaggleMinAmount().floatValue();
        Float haggleMaxAmount = grouponRulesDto.getHaggleMaxAmount().floatValue();
        BigDecimal haggleAmount = new BigDecimal(Math.random()*(haggleMaxAmount-haggleMinAmount)+haggleMinAmount);
        haggleAmount = haggleAmount.setScale(2,BigDecimal.ROUND_DOWN);

        taskEvent = commonService.createTaskEvent(taskInfo, TaskEventEnum.HAGGLE);
        taskEvent.setEventAmount(haggleAmount);
        //砍价奖励金额订单
        OrderInfo orderInfo = createHaggleRewardOrder(userId,taskId,haggleRewardAmount);
        //奖励金额入账
        baseResponse = userWalletService.userWalletBalanceUpdate(userId,orderInfo.getOrderCoinId(),orderInfo.getOrderNo(),
                orderInfo.getOrderType(),orderInfo.getOrderCoinAmount());
        if(ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())){
            taskEventMapper.insertSelective(taskEvent);
            //加入Dao
            daoService.joinDaoOrganize(userId,taskId);
            //
            orderInfo.setOrderStatus(OrderStatusEnum.SUCCESS.getCode());
            orderInfo.setSuccessTime(new Date());
            orderInfo.setUpdateTime(new Date());
            orderInfoMapper.insertSelective(orderInfo);

            HaggleRewardOrderRes haggleRewardOrderRes = new HaggleRewardOrderRes();
            haggleRewardOrderRes.setHaggleRewardAmount(haggleRewardAmount);
            haggleRewardOrderRes.setHaggleAmount(haggleAmount);
            baseResponse.setData(haggleRewardOrderRes);
        }
        return baseResponse;
    }

    @Override
    public BaseResponse orderList(OrderListReq orderListReq) {
        BaseResponse baseResponse = new BaseResponse();
        OrderListRes orderListRes = new OrderListRes();
        logger.info("订单列表查询业务,请求入参:{}",JSON.toJSONString(orderListReq));

        //分页
        int pageNum = orderListReq.getPageNum();
        int pageSize = orderListReq.getPageSize();
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("create_time desc");

        Long userId = orderListReq.getUserId();
        Long coinId = orderListReq.getCoinId();
        String orderType = orderListReq.getOrderType();
        String orderNo = orderListReq.getOrderNo();

        List<BaseOrderDto> baseOrderDtos = orderInfoMapper.selectOrderList(userId, orderNo, orderType, coinId);
        if(null != baseOrderDtos && baseOrderDtos.size() >0){
            PageInfo pageInfo = new PageInfo(baseOrderDtos);
            orderListRes.setTotalPage(pageInfo.getPages());
            orderListRes.setPageNum(pageNum);
            orderListRes.setPageSize(pageSize);
            orderListRes.setBaseOrderDtoList(baseOrderDtos);
            baseResponse.setData(orderListRes);
        }
        logger.info("订单列表查询业务,响应结果{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @Override
    public BaseResponse createOrder(OrderCreateReq orderCreateReq) {
        BaseResponse baseResponse = new BaseResponse();
        logger.info("订单创建业务,请求入参:{}",JSON.toJSONString(orderCreateReq));

        String taskId = orderCreateReq.getTaskId();
        TaskInfo taskInfo = taskInfoMapper.selectTaskByTaskId(taskId);
        //验证创建订单的任务是否符合条件
        baseResponse = verifyCreateOrderTask(orderCreateReq,taskInfo);
        if(!ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())){
            logger.info("订单创建业务,创建订单必要条件校验未通过:{}",JSON.toJSONString(baseResponse));
            return baseResponse;
        }
        //获得订单金额,拼团金额、直接购买交易金额、质押金额
        BigDecimal orderAmount = getOrderAmount(orderCreateReq,taskInfo);
        if(null == orderAmount){
            logger.info("订单创建业务,未正确获取到订单金额,创建订单失败");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.GET_ORDER_AMOUNT_ERROR);
            return baseResponse;
        }
        //创建订单
        Long userId = orderCreateReq.getUserId();
        String orderType = orderCreateReq.getOrderType();
        OrderInfo orderInfo = createOrder(taskId, orderAmount, userId, OrderTypeEnum.toEnum(orderType));
        logger.info("订单创建业务,创建的订单数据是:{}",JSON.toJSONString(orderInfo));
        //保存数据
        orderInfoMapper.insertSelective(orderInfo);
        //响应数据
        OrderCreateRes orderCreateRes = new OrderCreateRes();
        orderCreateRes.setOrderCoinId(orderInfo.getOrderCoinId());
        orderCreateRes.setOrderNo(orderInfo.getOrderNo());
        orderCreateRes.setOrderCoinAmount(orderInfo.getOrderCoinAmount());
        orderCreateRes.setOrderCoinName(orderInfo.getOrderCoinName());
        baseResponse.setData(orderCreateRes);
        logger.info("订单创建业务,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    private BigDecimal getOrderAmount(OrderCreateReq orderCreateReq, TaskInfo taskInfo) {
        String orderType = orderCreateReq.getOrderType();
        if(OrderTypeEnum.TRADE.getCode().equals(orderType)){
            GoodsInfo goodsInfo = goodsInfoMapper.selectByPrimaryKey(taskInfo.getGoodsId());
            return goodsInfo.getGoodsSalePrice();
        }else if(OrderTypeEnum.GROUPON_BUY.getCode().equals(orderType)){
            GoodsInfo goodsInfo = goodsInfoMapper.selectByPrimaryKey(taskInfo.getGoodsId());
            return goodsInfo.getGoodsSalePrice();
        }else if(OrderTypeEnum.PLEDGE.getCode().equals(orderType)){
            TaskRules taskRules = taskRulesMapper.selectTaskRulesByType(taskInfo.getTaskId(), TaskRulesTypeEnum.DISTRIBUTOR.getCode());
            DistributionRulesDto distributionRulesDto = JSON.parseObject(taskRules.getTaskRulesJson(),DistributionRulesDto.class);
            return distributionRulesDto.getPledgeAmount();
        }
        return null;
    }

    private BaseResponse verifyCreateOrderTask(OrderCreateReq orderCreateReq, TaskInfo taskInfo) {
        BaseResponse baseResponse = new BaseResponse();
        String orderType = orderCreateReq.getOrderType();
        String taskType = taskInfo.getTaskType();
        //任务类型和订单类型匹配检查，团购、直接交易购买、分销质押

        if((OrderTypeEnum.GROUPON_BUY.getCode().equals(orderType) && TaskTypeEnum.GROUPON.getCode().equals(taskType))
                || (OrderTypeEnum.TRADE.getCode().equals(orderType) && TaskTypeEnum.SALES.getCode().equals(taskType))
                || (OrderTypeEnum.TRADE.getCode().equals(orderType) && TaskTypeEnum.DISTRIBUTOR.getCode().equals(taskType))
                || (OrderTypeEnum.PLEDGE.getCode().equals(orderType) && TaskTypeEnum.DISTRIBUTOR.getCode().equals(taskType))){
        }else {
            logger.info("订单创建业务,当前订单类型:{} 与任务类型:{} 不匹配,创建订单失败",orderType,taskType);
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.ORDER_CREATE_ERROR);
            return baseResponse;
        }
        //检查任务发布方和当前用户是否一致
        Long taskOwnerId = taskInfo.getTaskOwnerId();
        Long userId = orderCreateReq.getUserId();
        if(userId.intValue() == taskOwnerId.intValue()){
            logger.info("订单创建业务,任务所属用户ID和当前用户ID一致,创建订单失败");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.OWNER_ORDER_CRATE_ERROR);
            return baseResponse;
        }
        //任务状态
        String taskStatus = taskInfo.getTaskStatus();
        if(!TaskStatusEnum.STARTED.getCode().equals(taskStatus)){
            logger.info("订单创建业务,任务非已开始状态或者任务已结束,创建订单失败");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.ORDER_CREATE_ERROR);
            return baseResponse;
        }
        return baseResponse;
    }

    private OrderInfo createHaggleRewardOrder(Long userId, String taskId, BigDecimal haggleRewardAmount) {
        UserInfo userInfo = userInfoMapper.queryUserByUserId(userId);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderNo(IDUtils.getOrderNo());
        orderInfo.setOrderType(OrderTypeEnum.REWARD.getCode());
        orderInfo.setOrderCoinId(3l);
        orderInfo.setOrderCoinAmount(haggleRewardAmount);
        orderInfo.setOrderCoinName("CCP");
        orderInfo.setUserId(userId);
        orderInfo.setPayeeAddress(userInfo.getUserWalletAddr());
        orderInfo.setPayerAddress("0x0000000000000000000000000000000000000000");
        orderInfo.setTaskId(taskId);
        orderInfo.setOrderStatus(OrderStatusEnum.UNPAID.getCode());
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        return orderInfo;
    }

    private OrderInfo createRechargeOrder(BaseCoin baseCoin, Long userId, BigDecimal amount) {
        UserInfo userInfo = userInfoMapper.queryUserByUserId(userId);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderNo(IDUtils.getOrderNo());
        orderInfo.setOrderType(OrderTypeEnum.RECHARGE.getCode());
        orderInfo.setOrderCoinId(baseCoin.getCoinId());
        orderInfo.setOrderCoinAmount(amount);
        orderInfo.setOrderCoinName(baseCoin.getCoinName());
        orderInfo.setUserId(userId);
        orderInfo.setPayeeAddress(userInfo.getUserWalletAddr());
        orderInfo.setPayerAddress("0x0000000000000000000000000000000000000000");
//        orderInfo.setTaskId(taskId);
        orderInfo.setOrderStatus(OrderStatusEnum.UNPAID.getCode());
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        return orderInfo;
    }

    private OrderInfo createOrder(String taskId, BigDecimal goodsSalePrice, Long userId, OrderTypeEnum orderTypeEnum) {
        UserInfo userInfo = userInfoMapper.queryUserByUserId(userId);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderNo(IDUtils.getOrderNo());
        orderInfo.setOrderType(orderTypeEnum.getCode());
        orderInfo.setOrderCoinId(3l);
        orderInfo.setOrderCoinAmount(goodsSalePrice);
        orderInfo.setOrderCoinName("CCP");
        orderInfo.setUserId(userId);
        orderInfo.setPayeeAddress("0x0000000000000000000000000000000000000000");
        orderInfo.setPayerAddress(userInfo.getUserWalletAddr());
        orderInfo.setTaskId(taskId);
        orderInfo.setOrderStatus(OrderStatusEnum.UNPAID.getCode());
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        return orderInfo;
    }

}
