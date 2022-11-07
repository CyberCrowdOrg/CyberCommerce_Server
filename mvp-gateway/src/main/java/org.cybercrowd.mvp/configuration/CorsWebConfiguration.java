package org.cybercrowd.mvp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 跨域配置
 */
@Component
public class CorsWebConfiguration implements Filter {

    @Value("${cross-domain.domain}")
    private String domain;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletResponse res = (HttpServletResponse) response;

        // 这里填写你允许进行跨域的主机ip,*表示所有（正式上线时可以动态配置具体允许的域名和IP）
        HttpServletRequest req = (HttpServletRequest) request;
        //获取来源网站
        String originStr = req.getHeader("Origin");
//        if(domain.indexOf(originStr)>-1){
//            //配置里面包含这个域名就可以跨域
//            res.addHeader("Access-Control-Allow-Origin", originStr);
//        }
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Credentials", "true");
        res.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        res.addHeader("Access-Control-Max-Age", "3600");

        //res.addHeader("Access-Control-Allow-Headers", "Content-Type,X-CAF-Authorization-Token,sessionToken,X-TOKEN,Role_Token,nclt,App-Version,Accept-Language,platform");
        res.addHeader("Access-Control-Allow-Headers", "*");

        if (req.getMethod().equals("OPTIONS")) {
            response.getWriter().println(true);
            return;
        }

        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }
}
