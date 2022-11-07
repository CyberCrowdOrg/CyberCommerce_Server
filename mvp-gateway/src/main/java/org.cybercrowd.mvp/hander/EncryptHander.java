package org.cybercrowd.mvp.hander;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import org.cybercrowd.mvp.controller.BaseController;
import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.util.DESUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class EncryptHander extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(EncryptHander.class);

    @Autowired
    private HttpServletRequest request;

    @Pointcut("@annotation(org.cybercrowd.mvp.common.anno.Encrypt)")
    public void pointCut() {

    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "pointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {

        handleAfterReturning(joinPoint, null, jsonResult);

    }

    private void handleAfterReturning(JoinPoint joinPoint, Object object, Object jsonResult) {

        //log.info("==>请求返回结果：{}", jsonResult);

        if (jsonResult instanceof BaseResponse) {

            //返回数据加密
            BaseResponse baseResponse = (BaseResponse) jsonResult;
            Object data = baseResponse.getData();

            if (ObjectUtil.isNotNull(data)) {
                String decryptData = DESUtils.encrypt(getLoginSessionId(request), JSON.toJSONString(baseResponse.getData()));
                baseResponse.setData(decryptData);
            }

        }

    }

    public static void main(String[] args) {

        String data = "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";
        String password = "miaole:gemi:r5gsfx:784a67ed49a1472aaed77516c6c46198";

        data = "R8MkJxpVKT454xcivVPIFslZ27HMLOdE1BqVr1rqth8eSMzVZSRuLOlDvhfDUDEE";
        password = "BitStore:USER_SESSION_ID:1007:77bf7d4f149a4b199fe17fef3e8509ba";
        String decrypt = DESUtils.decrypt(password, data);
        System.out.println(decrypt);


    }


}