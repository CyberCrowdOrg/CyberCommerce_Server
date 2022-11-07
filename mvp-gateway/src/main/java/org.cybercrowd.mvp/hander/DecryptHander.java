package org.cybercrowd.mvp.hander;

import org.cybercrowd.mvp.common.anno.Decrypt;
import org.cybercrowd.mvp.controller.BaseController;
import org.cybercrowd.mvp.util.DESUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;


/**
 * 请求参数解密处理
 */
//@Aspect
//@Component
public class DecryptHander extends BaseController implements HandlerMethodArgumentResolver {

    private static final Logger log = LoggerFactory.getLogger(DecryptHander.class);

    @Autowired
    private HttpServletRequest request;

    @Pointcut("@annotation(org.cybercrowd.mvp.common.anno.Decrypt)")
    public void pointCut() {

    }

    /**
     * 请求处理前执行
     *
     * @param joinPoint
     */
    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint) {

    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {

        return methodParameter.hasParameterAnnotation(Decrypt.class)
                || methodParameter.hasMethodAnnotation(Decrypt.class);

    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        /**
         * 因为这个注解是作用在方法和参数上的，所以要分情况
         */
        int count = methodParameter.hasMethodAnnotation(Decrypt.class)
                ? methodParameter.getMethodAnnotation(Decrypt.class).count()
                : methodParameter.getParameterAnnotation(Decrypt.class).count();

        return "5678";

    }

    public static void main(String[] args) {

        String data = "{\n" +
                "    \"nclt\": \"IDR\",\n" +
                "    \"amount\": \"10000\",\n" +
                "    \"userCardId\": \"1\",\n" +
                "    \"code\": \"1000\"\n" +
                "}";
        String key = "BitStore:USER_SESSION_ID:1007:77bf7d4f149a4b199fe17fef3e8509ba";
        String encrypt = DESUtils.encrypt(key, data);

        System.out.println(encrypt);


    }


}
