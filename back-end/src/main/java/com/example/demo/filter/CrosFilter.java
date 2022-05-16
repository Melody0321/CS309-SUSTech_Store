package com.example.demo.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class CrosFilter implements Filter {
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        this.parseAndStore("*", "GET,POST,HEAD,OPTIONS", "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers", "", "true", "1800", "false", "true");
//        this.filterConfig = filterConfig;
//        this.loggingEnabled = false;
//        if(filterConfig != null) {
//            String configAllowedOrigins = filterConfig.getInitParameter("cors.allowed.origins");
//            String configAllowedHttpMethods = filterConfig.getInitParameter("cors.allowed.methods");
//            String configAllowedHttpHeaders = filterConfig.getInitParameter("cors.allowed.headers");
//            String configExposedHeaders = filterConfig.getInitParameter("cors.exposed.headers");
//            String configSupportsCredentials = filterConfig.getInitParameter("cors.support.credentials");
//            String configPreflightMaxAge = filterConfig.getInitParameter("cors.preflight.maxage");
//            String configLoggingEnabled = filterConfig.getInitParameter("cors.logging.enabled");
//            String configDecorateRequest = filterConfig.getInitParameter("cors.request.decorate");
//            this.parseAndStore(configAllowedOrigins, configAllowedHttpMethods, configAllowedHttpHeaders, configExposedHeaders, configSupportsCredentials, configPreflightMaxAge, configLoggingEnabled, configDecorateRequest);
//        }
//    }
//    private Set<String> parseStringToSet(String data) {
//        String[] splits;
//        if(data != null && data.length() > 0) {
//            splits = data.split(",");
//        } else {
//            splits = new String[0];
//        }
//
//        HashSet set = new HashSet();
//        if(splits.length > 0) {
//            String[] arr$ = splits;
//            int len$ = splits.length;
//
//            for(int i$ = 0; i$ < len$; ++i$) {
//                String split = arr$[i$];
//                set.add(split.trim());
//            }
//        }
//
//        return set;
//    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
//        response.setHeader("Access-Control-Allow-Origin", "*");//允许跨域访问的域
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");//允许使用的请求方法，以逗号隔开
        response.setHeader("Access-Control-Max-Age", "3600");// 缓存此次请求的秒数
        //允许使用的请求方法，以逗号隔开
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Cache-Control,Pragma,Content-Type,Token");
        response.setHeader("Access-Control-Allow-Credentials","true");//是否允许请求带有验证信息
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }


}
