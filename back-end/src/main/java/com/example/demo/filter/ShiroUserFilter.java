package com.example.demo.filter;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.apache.shiro.web.util.WebUtils.saveRequest;

/**
 * 重写shiro的UserFilter，实现通过OPTIONS请求
 * UserFilter会过滤掉所有没有登录cookie的请求
 * PathMatchingFilter则不会
 * @author MDY
 */
public class ShiroUserFilter extends UserFilter {

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        // 放行 options 请求
        System.out.println("prehandling:"+ HttpMethod.OPTIONS.toString());
        if (HttpMethod.OPTIONS.toString().equals((httpServletRequest).getMethod())) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            return true;
        }

//        if (null==adminPermissionService) {
//            adminPermissionService = SpringContextUtils.getContext().getBean(AdminPermissionService.class);
//        }

        String requestAPI = getPathWithinApplication(request);
        System.out.println("访问接口：" + requestAPI);

        Subject subject = SecurityUtils.getSubject();

        if (!subject.isAuthenticated()) {
            System.out.println("需要登录");
            return false;
        }
        return true;
    }

    /**
     * 该方法会在验证失败后调用，这里由于是前后端分离，后台不控制页面跳转
     * 因此重写改成传输JSON数据
     */
    @Override
    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        saveRequest(request);
        setHeader((HttpServletRequest) request,(HttpServletResponse) response);
        PrintWriter out = response.getWriter();
        //out.println(JSONObject.toJSONString(ResultUtil.error(ExceptionEnum.IS_NOT_LOGIN)));
        out.flush();
        out.close();
    }

    /**
     * 为response设置header，实现跨域
     */
    private void setHeader(HttpServletRequest request, HttpServletResponse response){
        //跨域的header设置
        response.setHeader("Access-control-Allow-Origin", request.getHeader("http://localhost:8080"));
        response.setHeader("Access-Control-Allow-Methods", request.getMethod());
        response.setHeader("Access-Control-Allow-Credentials", "true");

        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        //防止乱码，适用于传输JSON数据
        response.setHeader("Content-Type","application/json;charset=UTF-8");
        response.setStatus(HttpStatus.OK.value());
        System.out.println("Access-Control-Allow-Credentials: True!!!");
    }

}

