package com.example.demo.controller;//package com.example.demo.controller;
//
//
//import com.example.demo.pojo.RegisterForm;
//import com.example.demo.pojo.User;
//import com.example.demo.result.Result;
//
//import com.example.demo.service.RegisterFormService;
//import com.example.demo.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.*;
//
//@Controller
//public class RegisterController1 extends HttpServlet{
//    @Autowired
//    //UserService userService;
//    EmailController emailController;
//    @Autowired
//    UserService userService;
//
//
//    @CrossOrigin
//    @PostMapping("api/register")
//    @ResponseBody
//    public Result register(@RequestBody User User) {
//        System.out.println(User.getUsername()+"\n"+User.getPassword()+"\n"+User.getEmail()+"\n"+User.getAddress());
//
//        boolean exist=userService.isExist(User.getUsername());
//        if(exist){
//
//            System.out.println("user already exist!");
//            return new Result(400);
//        }
//
//        //System.out.println("username is "+User.getUsername()+" already exists");
//        userService.add(User);
//        return new Result(200,"success");
//
//
//
////        // 生成盐,默认长度 16 位
////        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
////        // 设置 hash 算法迭代次数
////        int times = 2;
////        // 得到 hash 后的密码
////        String encodedPassword = new SimpleHash("md5", password, salt, times).toString();
////        // 存储用户信息，包括 salt 与 hash 后的密码
////
////        user.setSalt(salt);
////        user.setPassword(encodedPassword);
////        System.out.println(emailController.code+" "+User.getUsername());
////        if(User.getCode().equals(emailController.code)){
////
////            System.out.println("验证码错误！请重新输入");
////        }
//
//
//
//
//
//    }
//
//}