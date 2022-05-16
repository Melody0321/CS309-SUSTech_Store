package com.example.demo.controller;

import com.example.demo.dao.GoodsDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.pojo.Goods;
import com.example.demo.pojo.User;
import com.example.demo.result.Result;
import com.example.demo.service.GoodsService;
import com.example.demo.service.UserService;
import org.apache.catalina.manager.util.SessionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @CrossOrigin
    @PostMapping(value = "/api/login")
    @ResponseBody
    public Result login(@RequestBody User requestUser,HttpServletRequest request, HttpServletResponse response) {


        String username = requestUser.getUsername();
        Subject subject = SecurityUtils.getSubject();
//        subject.getSession().setTimeout(10000);
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, requestUser.getPassword());
        //usernamePasswordToken.setRememberMe(true);
        try {
            subject.login(usernamePasswordToken);
            User user1 = userService.getByName(username);
            int sid=user1.getSID();
            System.out.println(sid);
            //4、调用响应对象，根据验证结果将不同资源文件地址写入到响应头，交给浏览器
            response.setContentType("text/html;charset=UTF-8");
            if (sid != 0){
                //在判定来访用户身份合法后，通过请求对象向Tomcat申请为当前用户申请一个HttpSession

                request.getSession().setAttribute("sid",sid);

                System.out.println(request.toString()+"!!!!");


                HttpSession session =request.getSession();
                Enumeration<String> attrs = session.getAttributeNames();
                while(attrs.hasMoreElements()){
                    String name = attrs.nextElement().toString();
                    Object vakue = session.getAttribute(name);
                    System.out.println("------" + name + ":" + vakue +"-------\n");

                }
                //response.sendRedirect("/personal");
                //response.sendRedirect(request.getContextPath() + "/personal");
            }
            else{

                return new Result(404,"找不到该用户的学号");
            }
            return new Result(200);
        } catch (AuthenticationException e) {
            String message = "账号密码错误";
            return new Result(400);
        }

    }
    @RequestMapping("api/logout")
    public Result logout(HttpServletRequest request, HttpServletResponse response){

        Subject subject =SecurityUtils.getSubject();
        subject.logout();
        return new Result(200);
    }
    @ResponseBody
    @GetMapping(value = "api/authentication")
    public String authentication(){
        return "身份认证成功";
    }
//    @RequestMapping("/all")
//    public List<User> getAll(){
//        System.out.println("getAll!!!!");
//        System.out.println(userService.getClass().getName());
//        return userService.findAll();
//    }

    GoodsService goodsDAO;
    @RequestMapping("/all")
    public List<Goods> getAll(){
        System.out.println("getAll!!!!");
        System.out.println(userService.getClass().getName());
        return goodsDAO.findAll();
    }
    @RequestMapping("/test")
    public void a(){
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }




//    @CrossOrigin
//    @PostMapping(value = "/api/login")
//    @ResponseBody
//    protected Result doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        UserDAO dao;
//        int userId = 0;
//        //1、调用请求对象对请求体使用utf-8字符集进行重新编辑
//        request.setCharacterEncoding("UTF-8");
//        Subject subject = SecurityUtils.getSubject();
//        //2、调用请求对象读取请求体参数信息
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
//        usernamePasswordToken.setRememberMe(true);
//        try {
//            subject.login(usernamePasswordToken);
//            //3、调用Dao将查询验证信息推送到数据库服务器上
//            User user1 = userService.getByName(username);
//            String sid=user1.getSID();
//            //4、调用响应对象，根据验证结果将不同资源文件地址写入到响应头，交给浏览器
//            response.setContentType("text/html;charset=UTF-8");
//            if (sid != null){
//                //在判定来访用户身份合法后，通过请求对象向Tomcat申请为当前用户申请一个HttpSession
//                request.getSession().setAttribute("sid",sid);
//                //response.sendRedirect(request.getContextPath() + "/menus.html");
//            }
//            else{
//                return new Result(404,"找不到该用户的学号");
//            }
//            return new Result(200);
//        } catch (AuthenticationException e) {
//            String message = "账号密码错误";
//            return new Result(400);
//        }
//
//    }

//    @GetMapping("api/logout")
//    public Result logout() {
//        Subject subject = SecurityUtils.getSubject();
//        subject.logout();
//        String message = "成功登出";
//        return new Result(200,message);
//    }


}
