package org.cybercrowd.mvp.controller;

import com.alibaba.fastjson2.JSON;
import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.request.PayChannelListReq;
import org.cybercrowd.mvp.service.PayChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PayChannelController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(PayChannelController.class);

    @Autowired
    PayChannelService payChannelService;

    @PostMapping(value = "/channel/v1/list")
    @ResponseBody
    private BaseResponse payChannelList(@RequestBody PayChannelListReq payChannelListReq, HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        logger.info("支付通道列表查询接口,请求入参:{}", JSON.toJSONString(payChannelListReq));
        baseResponse = payChannelService.payChannelList(payChannelListReq);
        logger.info("支付通道列表查询接口,响应结果:{}",JSON.toJSONString(payChannelListReq));
        return baseResponse;
    }

}
