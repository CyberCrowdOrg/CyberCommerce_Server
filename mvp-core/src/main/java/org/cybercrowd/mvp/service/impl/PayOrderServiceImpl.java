package org.cybercrowd.mvp.service.impl;

import com.alibaba.fastjson2.JSON;
import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.request.PayOrderCreateReq;
import org.cybercrowd.mvp.dto.request.PayOrderUpdateReq;
import org.cybercrowd.mvp.enums.*;
import org.cybercrowd.mvp.mapper.OrderInfoMapper;
import org.cybercrowd.mvp.mapper.PayChannelMapper;
import org.cybercrowd.mvp.mapper.PayOrderMapper;
import org.cybercrowd.mvp.model.OrderInfo;
import org.cybercrowd.mvp.model.PayChannel;
import org.cybercrowd.mvp.model.PayOrder;
import org.cybercrowd.mvp.service.PayOrderService;
import org.cybercrowd.mvp.util.IDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service("payOrderService")
public class PayOrderServiceImpl implements PayOrderService {

    private Logger logger = LoggerFactory.getLogger(PayOrderServiceImpl.class);

    @Autowired
    PayOrderMapper payOrderMapper;
    @Autowired
    OrderInfoMapper orderInfoMapper;
    @Autowired
    PayChannelMapper payChannelMapper;

    @Autowired
    CommonService commonService;


    @Override
    public BaseResponse createPayOrder(PayOrderCreateReq payOrderCreateReq) {
        BaseResponse baseResponse = new BaseResponse();
        String orderNo = payOrderCreateReq.getOrderNo();
        Long userId = payOrderCreateReq.getUserId();
        //查询订单信息
        OrderInfo orderReq = new OrderInfo();
        orderReq.setOrderNo(orderNo);
        orderReq.setUserId(userId);
        OrderInfo orderInfo = orderInfoMapper.selectOrder(orderReq);
        if(null == orderInfo){
            logger.info("付款订单创建业务,交易订单未找到,创建订单失败");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.ORDER_NOT_FOUND_ERROR);
            return baseResponse;
        }
        //交易订单状态检查
        String orderStatus = orderInfo.getOrderStatus();
        baseResponse = checkOrderStatus(orderStatus);
        if(!ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())){
            return baseResponse;
        }
        //验证支付通道
        String channelId = payOrderCreateReq.getChannelId();
        PayChannel payChannel = payChannelMapper.findPayChannel(channelId);
        if(null == payChannel){
            logger.info("付款订单创建业务,支付通道未找到或者通道不可用");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.PAYCHANNEL_NOT_FOUND_ERROR);
            return baseResponse;
        }
        //创建付款订单
        PayOrder payOrder = createPayOrderInfo(orderInfo,payChannel,payOrderCreateReq,PayOrderTypeEnum.PAY);
        //处理任务、任务事务、DAO事务
        baseResponse = commonService.updateOrderAndEvent(orderInfo, OrderStatusEnum.SUCCESS);
        if(ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())){
            payOrder.setPaySuccessTime(new Date());
            payOrder.setPayStatus(PayOrderStatusEnum.SUCCESS.getCode());
            payOrderMapper.insertSelective(payOrder);
        }
        logger.info("付款订单创建业务,响应结果:{}", JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    private PayOrder createPayOrderInfo(OrderInfo orderInfo,PayChannel payChannel, PayOrderCreateReq payOrderCreateReq,
                                        PayOrderTypeEnum payOrderTypeEnum) {
        PayOrder payOrder = new PayOrder();
        payOrder.setOrderNo(orderInfo.getOrderNo());
        payOrder.setPayNo(IDUtils.getPayNo());
        payOrder.setPayMethod(payOrderCreateReq.getPayMethod());
        payOrder.setPayOrderType(payOrderTypeEnum.getCode());

        payOrder.setOrderCoinId(orderInfo.getOrderCoinId());
        payOrder.setOrderCoinName(orderInfo.getOrderCoinName());
        payOrder.setOrderAmount(orderInfo.getOrderCoinAmount());

        payOrder.setReceiveCoinId(payChannel.getSystemCoinId());
        payOrder.setReceiveCoinName(payChannel.getChannelCoinName());
        payOrder.setReceiveAmount(new BigDecimal("1000"));
        payOrder.setReceiveAddress("0x11232232322222222221111111111111");
        payOrder.setOrderExchangeRate(new BigDecimal("100000.00"));

        payOrder.setChannelId(payChannel.getChannelId());
        payOrder.setChannelFlowNo("");

        payOrder.setPayStatus(PayOrderStatusEnum.PENDING.getCode());
        payOrder.setCreateTime(new Date());
        payOrder.setUpdateTime(new Date());
        return payOrder;
    }

    private BaseResponse checkOrderStatus(String orderStatus) {
        BaseResponse baseResponse = new BaseResponse();
        if(OrderStatusEnum.PAYING.getCode().equals(orderStatus)){
            logger.info("付款订单创建业务,交易订单状态:{} 不能再创建付款订单",OrderStatusEnum.PAYING.getCode());
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.ORDER_STATUS_PAYING_ERROR);
        }else if(OrderStatusEnum.SUCCESS.getCode().equals(orderStatus)) {
            logger.info("付款订单创建业务,交易订单状态:{} 不能再创建付款订单",OrderStatusEnum.SUCCESS.getCode());
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.ORDER_STATUS_SUCCESS_ERROR);
        }else if(OrderStatusEnum.REFUND.getCode().equals(orderStatus)){
            logger.info("付款订单创建业务,交易订单状态:{} 不能再创建付款订单",OrderStatusEnum.REFUND.getCode());
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.ORDER_STATUS_REFUND_ERROR);
        }
        return baseResponse;
    }

    @Override
    public BaseResponse updatePayOrder(PayOrderUpdateReq payOrderUpdateReq) {
        return null;
    }
}
