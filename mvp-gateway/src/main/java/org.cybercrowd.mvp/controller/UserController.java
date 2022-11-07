package org.cybercrowd.mvp.controller;

import com.alibaba.fastjson2.JSON;
import org.apache.commons.lang3.StringUtils;
import org.cybercrowd.mvp.constant.HttpCookieConstants;
import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.request.*;
import org.cybercrowd.mvp.dto.response.UserLoginRes;
import org.cybercrowd.mvp.enums.ReturnCodeEnum;
import org.cybercrowd.mvp.enums.UserLoginTypeEnum;
import org.cybercrowd.mvp.enums.UserOriginEnum;
import org.cybercrowd.mvp.service.GoodsService;
import org.cybercrowd.mvp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

@RestController
public class UserController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    UserService userService;
    @Autowired
    GoodsService goodsService;

    /**
     * 用户登录
     * @param userLoginReq
     * @param response
     * @return
     */
    @RequestMapping("/user/v1/login")
    @ResponseBody
    public BaseResponse userLogin(UserLoginReq userLoginReq, HttpServletResponse response){
        logger.info("用户登录接口,请求入参:{}",JSON.toJSONString(userLoginReq));
        BaseResponse baseResponse = new BaseResponse();
        String loginType = userLoginReq.getLoginType();
        if(StringUtils.isEmpty(loginType) ||
                (!UserLoginTypeEnum.WEB2.getCode().equals(loginType)
                        && !UserLoginTypeEnum.WEB3.getCode().equals(loginType))){
            logger.info("用户登录接口,参数 login type 不能为空或者参数错误");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        String userOrigin = userLoginReq.getUserOrigin();
        if(StringUtils.isEmpty(userOrigin) || null == UserOriginEnum.toEnum(userOrigin)){
            logger.info("用户登录接口,参数 user origin 不能为空或者参数错误");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        String account = userLoginReq.getAccount();
        if(StringUtils.isEmpty(account) && UserLoginTypeEnum.WEB3.getCode().equals(loginType)){
            logger.info("用户登录接口,参数 account 不能为空或者参数错误");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        String loginKey = userLoginReq.getLoginKey();
        if(StringUtils.isEmpty(loginKey) && UserLoginTypeEnum.WEB2.getCode().equals(loginType)){
            logger.info("用户登录接口,参数 login key 不能为空或者参数错误");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }

        baseResponse = userService.userLogin(userLoginReq);
        if(ReturnCodeEnum.SUCCESS.getCode().equals(baseResponse.getReturnCode())){
            UserLoginRes  userLoginRes = (UserLoginRes) baseResponse.getData();
            //设置Cookie 有效时间 7天
            Cookie cookie = new Cookie(HttpCookieConstants.LOGIN_SESSION_ID, userLoginRes.getLoginToken());
            cookie.setMaxAge(3600 * 24 * 7);
            cookie.setPath("/");
            response.addCookie(cookie);
            // 设置redis token有效期有效时间 7天
            redisTemplate.opsForValue().set(userLoginRes.getLoginToken(), userLoginRes.getUserId() + "==" + userLoginRes.getUserWalletAddr(), 7, TimeUnit.DAYS);
        }
        logger.info("用户登录接口,响应结果:{}", JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @PostMapping(value = "/user/v1/loginbind")
    @ResponseBody
    public BaseResponse userLoginBind(@RequestBody UserLoginBindReq userLoginBindReq,HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        logger.info("用户登录方式绑定接口,请求入参:{}",JSON.toJSONString(userLoginBindReq));
        userLoginBindReq.setUserId(getUserId(request));
        String loginType = userLoginBindReq.getLoginType();
        if(StringUtils.isEmpty(loginType) ||
                (!UserLoginTypeEnum.WEB2.getCode().equals(loginType)
                        && !UserLoginTypeEnum.WEB3.getCode().equals(loginType))){
            logger.info("用户登录方式绑定接口,,参数 login type 不能为空或者参数错误");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        String userOrigin = userLoginBindReq.getUserOrigin();
        if(StringUtils.isEmpty(userOrigin) || null == UserOriginEnum.toEnum(userOrigin)){
            logger.info("用户登录方式绑定接口,,参数 user origin 不能为空或者参数错误");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        String account = userLoginBindReq.getAccount();
        if(StringUtils.isEmpty(account) && UserLoginTypeEnum.WEB3.getCode().equals(loginType)){
            logger.info("用户登录方式绑定接口,,参数 account 不能为空或者参数错误");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        String loginKey = userLoginBindReq.getLoginKey();
        if(StringUtils.isEmpty(loginKey) && UserLoginTypeEnum.WEB2.getCode().equals(loginType)){
            logger.info("用户登录方式绑定接口,,参数 login key 不能为空或者参数错误");
            baseResponse.setReturnCodeEnum(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR);
            return baseResponse;
        }
        baseResponse = userService.userLoginBind(userLoginBindReq);
        logger.info("用户登录方式绑定接口,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @GetMapping("/user/v1/personal")
    @ResponseBody
    public BaseResponse userPersonalInfo(HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        Long userId = getUserId(request);
        baseResponse = userService.userPersonal(userId);
        logger.info("用户个人中心接口,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @PostMapping("/user/v1/store")
    @ResponseBody
    public BaseResponse userStore(@RequestBody UserGoodsStoreReq userGoodsStoreReq, HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        userGoodsStoreReq.setUserId(getUserId(request));
        logger.info("我的店铺查询接口,请求入参:{}", JSON.toJSONString(userGoodsStoreReq));
        baseResponse = goodsService.userGoodsStore(userGoodsStoreReq);
        logger.info("我的店铺查询接口,响应结果:{}", com.alibaba.fastjson.JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @PostMapping("/user/v1/groupon")
    @ResponseBody
    public BaseResponse userGroupon(@RequestBody UserGrouponReq userGrouponReq, HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        userGrouponReq.setUserId(getUserId(request));
        logger.info("我的拼团查询接口,请求入参:{}", JSON.toJSONString(userGrouponReq));
        baseResponse = goodsService.userGroupon(userGrouponReq);
        logger.info("我的拼团查询接口,响应结果:{}", JSON.toJSONString(baseResponse));
        return baseResponse;
    }

    @PostMapping(value = "/user/v1/distributor")
    @ResponseBody
    public BaseResponse userDistributor(@RequestBody UserDistributionReq userDistributionReq,HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();
        userDistributionReq.setUserId(getUserId(request));
        logger.info("用户分销列表查询接口,请求入参:{}",JSON.toJSONString(userDistributionReq));
        baseResponse = goodsService.userDistribution(userDistributionReq);
        logger.info("用户分销列表查询接口,响应结果:{}",JSON.toJSONString(baseResponse));
        return baseResponse;
    }

//    @RequestMapping(value = "/user/v1/test")
//    @ResponseBody
//    public String test() throws Exception{
//        Web3j web3j = Web3j.build(new HttpService("https://goerli.infura.io/v3/5d5d3e2fc4364fb9b2be5beec22ac14d"));
//        Credentials credentials = Credentials.create("e3fe2423c0e418304a31d80ae7ba958bd5ce959fe9fe931271ee5c067f1edd83");
//
//        TransactionReceipt transactionReceipt = Transfer.
//                sendFunds(web3j, credentials, "0x12975169Fc543c51b7dB76Bf36938df5bc9dd5db",
//                        new BigDecimal("0.1"), Convert.Unit.ETHER).send();
//        logger.info("");
//        return "OK";
//    }

}
