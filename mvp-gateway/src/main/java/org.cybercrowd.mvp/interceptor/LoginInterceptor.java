package org.cybercrowd.mvp.interceptor;


import com.alibaba.fastjson.JSON;
import org.cybercrowd.mvp.constant.HttpCookieConstants;
import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.enums.ReturnCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


/**
 * 登录拦截器
 * WebMvcConfigurerAdapter自从Spring5.0版本开始已经不建议使用了，所以这里是实现了 WebMvcConfigurer 实现登录拦截功能
 * 如果是继承 WebMvcConfigurationSupport (springboot2),则会导致某些自动配置类失效，数据返回默认编码变为 ISO-8859-1，解决方法 打开 ‘添加转换器’ 注释代码
 */
@Configuration
public class LoginInterceptor extends WebMvcConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 添加转换器
     */
    @Autowired
    private StringHttpMessageConverter stringHttpMessageConverter;
    @Autowired
    private MappingJackson2HttpMessageConverter httpMessageConverter;

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (int i = 0; i < converters.size(); i++) {
            if (converters.get(i) instanceof StringHttpMessageConverter) {
                converters.set(i, stringHttpMessageConverter);
            }
            if (converters.get(i) instanceof MappingJackson2HttpMessageConverter) {
                converters.set(i, httpMessageConverter);
            }
        }
    }


    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());
        // 排除配置
        addInterceptor.excludePathPatterns("/user/v1/login");
        addInterceptor.excludePathPatterns("/goods/v1/list");
        addInterceptor.excludePathPatterns("/goods/v1/details");
        addInterceptor.excludePathPatterns("/dao/v1/all");

        addInterceptor.excludePathPatterns("/user/v1/test");
        // 拦截配置
        addInterceptor.addPathPatterns("/user/**");
        addInterceptor.addPathPatterns("/dao/**");
        addInterceptor.addPathPatterns("/goods/**");
        addInterceptor.addPathPatterns("/order/**");
        addInterceptor.addPathPatterns("/channel/**");
        addInterceptor.addPathPatterns("/pay/**");
        addInterceptor.addPathPatterns("/proposal/**");
        addInterceptor.addPathPatterns("/task/**");


    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws IOException {
//            Cookie[] cookies = request.getCookies();
//
//            if (cookies == null || cookies.length == 0) {
//                pleaseLogin(request, response);
//                log.info("用户未登录,无cookies信息，请求路径：{}", request.getRequestURI());
//                return false;
//            }
//
//            log.info("cookie全部信息:{}",JSON.toJSONString(cookies));
//
//            for (Cookie cookie : cookies) {
//                log.info("cookieName:{}", cookie.getName());
//                log.info("cookieValue:{}", cookie.getValue());
//                //System.out.println("cookieName:" + cookie.getName());
//                //System.out.println("cookieValue:" + cookie.getValue());
//            }
//
//            for (Cookie cookie : cookies) {
//
//                String key = cookie.getName();
//                if (HttpCookieConstants.LOGIN_SESSION_ID.equals(key)) {
//                    String value = cookie.getValue();
//                    String account = stringRedisTemplate.opsForValue().get(value);
//                    if (StringUtils.isEmpty(account)) {
//                        pleaseLogin(request, response);
//                        log.info("用户未登录,服务端登录session已失效，请求路径：{}", request.getRequestURI());
//                        return false;
//                    } else {
//                        // TODO 是否登录判断是否为有效设备
//                        // 设置该key 增加一个是否有效设备的标识
//                    }
//                    return true;
//                }
//            }
//
//            // 未匹配到cookie,返回false
//            log.info("用户未登录,客户端登录session已失效，请求路径：{}", request.getRequestURI());
//            pleaseLogin(request, response);
//            return false;

            String header = request.getHeader(HttpCookieConstants.LOGIN_SESSION_ID);

            if(StringUtils.isEmpty(header)){
                pleaseLogin(request, response);
                log.info("用户未登录,服务端登录session已失效，请求路径：{}", request.getRequestURI());
                return false;
            }else{
                String account = stringRedisTemplate.opsForValue().get(header);
                if (StringUtils.isEmpty(account)) {
                    pleaseLogin(request, response);
                    log.info("用户未登录,服务端登录session已失效，请求路径：{}", request.getRequestURI());
                    return false;
                }
            }
            return true;
        }
    }

    private void pleaseLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // String url = request.getContextPath() + "/user/v1/login";
        // response.sendRedirect(url);
        response.setCharacterEncoding("utf-8");
//        response.setContentType("text/html; charset=utf-8");
        PrintWriter writer = response.getWriter();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setReturnCode(ReturnCodeEnum.PLEASE_LOGIN.getCode());
        baseResponse.setReturnMsg(ReturnCodeEnum.PLEASE_LOGIN.getMsg());
        writer.write(JSON.toJSONString(baseResponse));
    }
}
