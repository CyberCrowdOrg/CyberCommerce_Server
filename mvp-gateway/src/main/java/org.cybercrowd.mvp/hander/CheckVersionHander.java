package org.cybercrowd.mvp.hander;//package com.voucher.platform.hander;
//
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import com.voucher.platform.controller.BaseController;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//
//
///**
// * 版本检查
// */
//@Aspect
//@Component
//public class CheckVersionHander extends BaseController {
//
//    private static final Logger log = LoggerFactory.getLogger(CheckVersionHander.class);
//
//    @Autowired
//    private HttpServletRequest request;
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    @Pointcut("@annotation(org.role.base.common.anno.CheckVersion)")
//    public void pointCut() {
//
//    }
//
//
////    // 方法执行开始之前
////    @Before("pointCut()")
////    public void logStart(JoinPoint joinPoint) {
////
////        log.info("app版本检查,方法执行前,版本检查");
////        String language = getLanguage(request);
////        String appVersion = getAppVersion(request);
////        String platform = getPlatform(request);
////
////        if (StrUtil.isEmpty(language) || StrUtil.isEmpty(appVersion) || StrUtil.isEmpty(platform)) {
////            log.warn("app版本检查,未获取到请求头相关信息");
////            throw new CheckVersionException();
////        }
////
////        if (!language.equals("id-ID") && !language.equals("en-US")) {
////            language = "en-US";
////        }
////
////        String newVersion = "";
////        String downLoadUrl = "";
////        String content = "";
////        String isForceUpdate;
////
////        // Android en-US iOS
////        String keyPrefix = RedisKeyConstant.SYSTEM_CONFIG + platform + ":" + language + ":";
////
////        newVersion = stringRedisTemplate.opsForValue().get(keyPrefix + "version");
////        downLoadUrl = stringRedisTemplate.opsForValue().get(keyPrefix + "url");
////        content = stringRedisTemplate.opsForValue().get(keyPrefix + "content");
////        isForceUpdate = stringRedisTemplate.opsForValue().get(keyPrefix + "forceUpdate");
////
////        CheckVersionResDTO checkVersionResDTO = new CheckVersionResDTO();
////        checkVersionResDTO.setVersion(newVersion);
////        checkVersionResDTO.setUrl(downLoadUrl);
////        checkVersionResDTO.setContent(content);
////        checkVersionResDTO.setNeedUpdate(BooleanEnum.TRUE.getMapCode().toString());
////        checkVersionResDTO.setIsForceUpdate(isForceUpdate);
////
////        log.info("app版本检查：平台:{},用户版本号:{},新版本号:{}", platform, appVersion, newVersion);
////
////        if (StrUtil.isEmpty(appVersion)) {
////            log.warn("app版本检查,请求的版本号为空，建议更新");
////            checkVersionResDTO.setNeedUpdate(BooleanEnum.TRUE.getMapCode().toString());
////            throw new CheckVersionException(JSON.toJSONString(checkVersionResDTO));
////        }
////
////
////        if (StrUtil.isNotBlank(newVersion)) {
////
////            String[] oldVer = appVersion.split("_")[1].split("\\.");
////            // String[] oldVer = appVersion.split("_");
////            String[] newVer = newVersion.split("_")[1].split("\\.");
////            // String[] newVer = newVersion.split("_");
////
////
////            for (int i = 0; i < newVer.length; i++) {
////
////                if (Integer.valueOf(oldVer[i]) > Integer.valueOf(newVer[i])) {
////                    // 版本大于当前版本
////                    //log.info("版本不一致，建议更新");
////                    // throw new CheckVersionException(JSON.toJSONString(checkVersionResDTO));
////                    break;
////                } else if (Integer.valueOf(newVer[i]) > Integer.valueOf(oldVer[i])) {
////
////                    log.info("app版本检查,版本不一致，建议更新");
////                    throw new CheckVersionException(JSON.toJSONString(checkVersionResDTO));
////
////                }
////            }
////        }
////
////
////    }
//
//}
