package org.cybercrowd.mvp.controller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.request.PayOrderCreateReq;
import org.cybercrowd.mvp.enums.PayMethodEnum;
import org.cybercrowd.mvp.enums.ReturnCodeEnum;
import org.cybercrowd.mvp.service.PayOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PayOrderController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(PayOrderController.class);

    @Autowired
    PayOrderService payOrderService;


    @PostMapping(value = "/pay/v1/create.order")
    @ResponseBody
    public BaseResponse createPayOrder(@RequestBody PayOrderCreateReq payOrderCreateReq, HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        payOrderCreateReq.setUserId(getUserId(request));

        String orderNo = payOrderCreateReq.getOrderNo();
        if(StringUtils.isEmpty(orderNo)){
            logger.info("支付订单创建接口,交易订单号不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }

        String channelId = payOrderCreateReq.getChannelId();
        if(StringUtils.isEmpty(channelId)){
            logger.info("支付订单创建接口,支付渠道ID不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }

        String payMethod = payOrderCreateReq.getPayMethod();
        if(StringUtils.isEmpty(payMethod)){
            logger.info("支付订单创建接口,支付方式不能为空");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }else if(null == PayMethodEnum.toEnum(payMethod)){
            logger.info("支付订单创建接口,支付方式:{} 错误",payMethod);
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        baseResponse = payOrderService.createPayOrder(payOrderCreateReq);
        logger.info("支付订单创建接口,响应结果:{}", JSON.toJSONString(baseResponse));
        return baseResponse;
    }
}
