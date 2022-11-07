package org.cybercrowd.mvp.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import org.cybercrowd.mvp.constant.HttpCookieConstants;
import org.cybercrowd.mvp.common.exception.RequestException;
import org.cybercrowd.mvp.constant.HttpCookieConstants;
import org.cybercrowd.mvp.constant.HttpHeaderConstants;
import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.enums.ReturnCodeEnum;
import org.cybercrowd.mvp.util.DESUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    private static final Logger log = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Long getUserId(HttpServletRequest request) {
//        Cookie[] cookies = request.getCookies();
//
//        if (cookies == null) {
//            throw new NotLoginException();
//        }
//
//        for (Cookie cookie : cookies) {
//
//            if (HttpCookieConstants.LOGIN_SESSION_ID.equals(cookie.getName())) {
//                String value = cookie.getValue();
//                String userInfo = stringRedisTemplate.opsForValue().get(value);
//                if (StringUtils.isEmpty(userInfo)) {
//                    throw new NotLoginException();
//                }
//                return Long.valueOf(userInfo.split("==")[0]);
//            }
//        }
//        throw new NotLoginException();

        String headerValue = request.getHeader(HttpCookieConstants.LOGIN_SESSION_ID);

        if(StringUtils.isEmpty(headerValue)){
//            throw new NotLoginException();
            return null;
        }else{
            String userInfo = stringRedisTemplate.opsForValue().get(headerValue);
            if (StringUtils.isEmpty(userInfo)) {
                return null;
            }else{
                return Long.parseLong(userInfo.split("==")[0]);
            }
        }
    }

    public Long getUserInfo(HttpServletRequest request) {
//        Cookie[] cookies = request.getCookies();
//
//        if (cookies == null) {
//            return null;
//        }
//
//        for (Cookie cookie : cookies) {
//            if (HttpCookieConstants.LOGIN_SESSION_ID.equals(cookie.getName())) {
//                String value = cookie.getValue();
//                String userInfo = stringRedisTemplate.opsForValue().get(value);
//                return Long.valueOf(userInfo);
//            }
//        }
//        return null;
        String headerValue = request.getHeader(HttpCookieConstants.LOGIN_SESSION_ID);

        if(StringUtils.isEmpty(headerValue)){
            return null;
        }else{
            String userInfo = stringRedisTemplate.opsForValue().get(headerValue);
            return Long.valueOf(userInfo);
        }
    }


    public String getLoginSessionId(HttpServletRequest request) {

//        Cookie[] cookies = request.getCookies();
//        if (null == cookies) {
//            return null;
//        }
//        for (Cookie cookie : cookies) {
//            if (HttpCookieConstants.LOGIN_SESSION_ID.equals(cookie.getName())) {
//                String value = cookie.getValue();
//                if (StringUtils.isEmpty(value)) {
//                    return null;
//                }
//                return value;
//            }
//        }
//        return null;
        String headerValue = request.getHeader(HttpCookieConstants.LOGIN_SESSION_ID);

        if(StringUtils.isEmpty(headerValue)){
            return null;
        }else{
            return headerValue;
        }
    }


    public void signOut(HttpServletRequest request) {

//        Cookie[] cookies = request.getCookies();
//        if (null == cookies) {
//            return;
//        }
//        for (Cookie cookie : cookies) {
//            if (HttpCookieConstants.LOGIN_SESSION_ID.equals(cookie.getName())) {
//                String value = cookie.getValue();
//                if (StringUtils.isEmpty(value)) {
//                    return;
//                }
//                stringRedisTemplate.delete(value);
//            }
//        }
//        return;
        String headerValue = request.getHeader(HttpCookieConstants.LOGIN_SESSION_ID);

        if(StringUtils.isEmpty(headerValue)){
            return;
        }else{
            stringRedisTemplate.delete(headerValue);
        }
    }


    /**
     * 获取平台名称（Android OR IOS）
     *
     * @param request
     * @return
     */
    public String getPlatform(HttpServletRequest request) {

        // 获取 platform
        String platform = request.getHeader(HttpHeaderConstants.PLATFORM);

        if (StrUtil.isEmpty(platform)) {
            platform = "";
        }

        return platform;

    }

    /**
     * 获取App版本号
     *
     * @param request
     * @return
     */
    public String getAppVersion(HttpServletRequest request) {

        // 获取region
        String appVersion = request.getHeader(HttpHeaderConstants.APP_VERSION);

        if (StrUtil.isEmpty(appVersion)) {
            appVersion = "";
        }

        return appVersion;

    }

    /**
     * 接口请求参数解密
     *
     * @param data
     * @param request
     * @param targetClass
     * @param <T>
     * @return
     */
    public <T> T decryptParams(String data, HttpServletRequest request, Class<T> targetClass) {

        String loginSessionId = getLoginSessionId(request);
        String decryptStr = DESUtils.decrypt(loginSessionId, data);
        if (StrUtil.isNotEmpty(decryptStr)) {
            log.info("接口请求参数解密结果:{}", decryptStr);
            if (data.equals(decryptStr)) {
                throw new RequestException(ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR.getCode(), ReturnCodeEnum.REQUEST_PARAMETER_NOT_NULL_ERROR.getMsg());
            }

            T t = JSON.parseObject(decryptStr, targetClass);
            return t;

        }

        return null;
    }


    public boolean isLogin(HttpServletRequest request) {

        Long userId = getUserId(request);
        boolean loginFlag = isLogin(userId);
        return loginFlag;

    }

    private boolean isLogin(Long userId) {
        if (StringUtils.isEmpty(userId)) {
            return false;
        }
        return true;
    }

    /**
     * 获取App版本号
     *
     * @param request
     * @return
     */
    public String getLanguage(HttpServletRequest request) {

        // 获取 language
        String language = request.getHeader(HttpHeaderConstants.ACCEPT_LANGUAGE);

        if (StrUtil.isEmpty(language)) {
            language = "";
        }
        return language;
    }

    /**
     * 返回数据封装
     *
     * @param data
     * @return
     */
    public BaseResponse returnOk(Object data) {

        BaseResponse baseResponse = new BaseResponse();

        baseResponse.setReturnCode(ReturnCodeEnum.SUCCESS.getCode());
        baseResponse.setReturnMsg(ReturnCodeEnum.SUCCESS.getMsg());
//        baseResponse.setReturnMsg(MessageUtils.get(baseResponse.getReturnCode()));
        baseResponse.setData(data);
        return baseResponse;

    }

    /**
     * 返回成功请求
     *
     * @return
     */
    public BaseResponse returnOk() {

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setReturnCode(ReturnCodeEnum.SUCCESS.getCode());
        baseResponse.setReturnMsg(baseResponse.getReturnCode());
//        baseResponse.setReturnMsg(MessageUtils.get(baseResponse.getReturnCode()));
        return baseResponse;

    }

    /**
     * 返回数据封装
     *
     * @param returnCodeEnum
     * @return
     */
    public BaseResponse returnBaseResponse(ReturnCodeEnum returnCodeEnum) {

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setReturnCode(returnCodeEnum.getCode());
        baseResponse.setReturnMsg(returnCodeEnum.getMsg());
//        baseResponse.setReturnMsg(MessageUtils.get(baseResponse.getReturnCode()));
        return baseResponse;

    }

}
