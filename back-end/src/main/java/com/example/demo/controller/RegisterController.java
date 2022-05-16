package com.example.demo.controller;


import com.example.demo.pojo.RegisterForm;
import com.example.demo.pojo.User;
import com.example.demo.result.Result;

import com.example.demo.service.RegisterFormService;
import com.example.demo.service.UserService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;

@Controller
public class RegisterController extends HttpServlet{
    @Autowired
    //UserService userService;
    EmailController emailController;
    @Autowired
    UserService userService;
    @Autowired
    RegisterFormService registerFormService;

    @CrossOrigin
    @PostMapping("api/register")
    @ResponseBody
    public Result register(@RequestBody RegisterForm registerForm) {
        System.out.println("registerForm:");
        System.out.println(registerForm.getUsername());
        System.out.println(registerForm.getPassword());
        System.out.println(registerForm.getCheckPass());
        System.out.println(registerForm.getSID());
        System.out.println(registerForm.getAddress());
        System.out.println(registerForm.getEmail());
        System.out.println(registerForm.getCode());
        User User=new User(registerForm.getUsername(),registerForm.getPassword(),registerForm.getSID(),registerForm.getEmail(),registerForm.getAddress());
        System.out.println(User.getUsername()+"\n"+User.getPassword()+"\n"+User.getEmail()+"\n"+User.getAddress());
        User ex=userService.getByName(User.getUsername());
        User ex1=userService.getBySid(User.getSID());
        boolean exist;
        if(null!=ex){
            exist=true;
            System.out.println("name exist!");
            return new Result(400,"username already exists");
        }
        if(null!=ex1){
            exist=true;
            System.out.println("sid exist!");
            return new Result(402,"sid already exists");
        }
        else
            exist=false;
        //System.out.println("username is "+User.getUsername()+" already exists");




        // 生成盐,默认长度 16 位
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        // 设置 hash 算法迭代次数
        int times = 2;
        // 得到 hash 后的密码
        String encodedPassword = new SimpleHash("md5", User.getPassword(), salt, times).toString();
        // 存储用户信息，包括 salt 与 hash 后的密码

        User.setSalt(salt);
        User.setPassword(encodedPassword);
        System.out.println(emailController.code+" "+User.getUsername());
        if(Integer.parseInt(registerForm.getCode())!=(emailController.code)){
            System.out.println(registerForm.getCode());
            System.out.println(emailController.code);
            System.out.println("验证码错误！请重新输入");
            return new Result(401);
        }

        userService.add(User);
        return new Result(200);




    }

}