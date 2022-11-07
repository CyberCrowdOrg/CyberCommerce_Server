package org.cybercrowd.mvp.controller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.request.*;
import org.cybercrowd.mvp.enums.OrderTypeEnum;
import org.cybercrowd.mvp.enums.ReturnCodeEnum;
import org.cybercrowd.mvp.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@RestController
public class OrderController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderService orderService;

//    @PostMapping(value = "/order/v1/groupon")
//    @ResponseBody
//    public BaseResponse grouponOrder(@RequestBody GrouponOrderReq grouponOrderReq, HttpServletRequest request){
//        BaseResponse baseResponse = new BaseResponse();
//        grouponOrderReq.setUserId(getUserId(request));
//        logger.info("拼团购买订单接口,请求入参:{}", JSON.toJSONString(grouponOrderReq));
//
//        String taskId = grouponOrderReq.getTaskId();
//        if(StringUtils.isEmpty(taskId)){
//            logger.info("拼团购买订单接口, taskId 不能为空");
//            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
//            return baseResponse;
//        }
//        baseResponse = orderService.grouponOrder(grouponOrderReq);
//        logger.info("拼团购买订单接口,响应结果:{}",JSON.toJSONString(baseResponse));
//        return baseResponse;
//    }
//
//    @PostMapping(value = "/order/v1/separate")
//    @ResponseBody
//    public BaseResponse separateOrder(@RequestBody SeparateOrderReq separateOrderReq, HttpServletRequest request){
//        BaseResponse baseResponse = new BaseResponse();
//        separateOrderReq.setUserId(getUserId(request));
//        logger.info("单独购买订单接口,请求入参:{}", JSON.toJSONString(separateOrderReq));
//
//        String taskId = separateOrderReq.getTaskId();
//        if(StringUtils.isEmpty(taskId)){
//            logger.info("单独购买订单接口, taskId 不能为空");
//            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
//            return baseResponse;
//        }
//        baseResponse = orderService.separateOrder(separateOrderReq);
//        logger.info("单独购买订单接口,响应结果:{}",JSON.toJSONString(baseResponse));
//        return baseResponse;
//    }

    @PostMapping(value = "/order/v1/create")
    @ResponseBody
    public BaseResponse createOrder(@RequestBody OrderCreateReq orderCreateReq,HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        orderCreateReq.setUserId(getUserId(request));

        String taskId = orderCreateReq.getTaskId();
        if(StringUtils.isEmpty(taskId)){
            logger.info("订单创建接口,任务ID不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }

        String orderType = orderCreateReq.getOrderType();
        if(StringUtils.isEmpty(orderType)){
            logger.info("订单创建接口,订单类型不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }else if(null == OrderTypeEnum.toEnum(orderType)){
            logger.info("订单创建接口,订单类型:{} 错误",orderType);
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }

        baseResponse = orderService.createOrder(orderCreateReq);
        logger.info("订单创建接口,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @PostMapping(value = "/order/v1/recharge")
    @ResponseBody
    public BaseResponse rechargeOrder(@RequestBody RechargeOrderReq rechargeOrderReq, HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        rechargeOrderReq.setUserId(getUserId(request));
        logger.info("充值订单接口,请求入参:{}", JSON.toJSONString(rechargeOrderReq));

        BigDecimal amount = rechargeOrderReq.getAmount();
        Long coinId = rechargeOrderReq.getCoinId();

        if(null == coinId || 0 == coinId.intValue()){
            logger.info("充值订单接口,coinId 参数错误");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        if(null == amount || BigDecimal.ZERO.compareTo(amount) >= 0){
            logger.info("充值订单接口, amount 参数错误");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }

        baseResponse = orderService.rechargeOrder(rechargeOrderReq);
        logger.info("充值订单接口,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @PostMapping(value = "/order/v1/haggle.reward")
    @ResponseBody
    public BaseResponse rechargeOrder(@RequestBody HaggleRewardOrderReq haggleRewardOrderReq, HttpServletRequest request) {
        BaseResponse baseResponse = new BaseResponse();
        haggleRewardOrderReq.setUserId(getUserId(request));
        logger.info("砍价奖励订单接口,请求入参:{}",JSON.toJSONString(haggleRewardOrderReq));
        String taskId = haggleRewardOrderReq.getTaskId();
        if(StringUtils.isEmpty(taskId)){
            logger.info("砍价奖励订单接口, taskId 不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        baseResponse = orderService.haggleRewardOrder(haggleRewardOrderReq);
        logger.info("砍价奖励订单接口,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @PostMapping(value = "/order/v1/list")
    @ResponseBody
    public BaseResponse orderList(@RequestBody OrderListReq orderListReq,HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        orderListReq.setUserId(getUserId(request));
        logger.info("订单列表查询接口,请求入参:{}",JSON.toJSONString(orderListReq));
        String orderType = orderListReq.getOrderType();
        if(!StringUtils.isEmpty(orderType) && null == OrderTypeEnum.toEnum(orderType)){
            logger.info("订单列表查询接口,order type:{} 参数错误",orderType);
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        baseResponse = orderService.orderList(orderListReq);
        logger.info("订单列表查询接口,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }
}
