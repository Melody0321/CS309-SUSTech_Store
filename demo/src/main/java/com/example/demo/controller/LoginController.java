package com.example.demo.controller;

import com.example.demo.pojo.User;
import com.example.demo.result.Result;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @CrossOrigin
    @PostMapping(value = "/api/login")
    @ResponseBody
    public Result login(@RequestBody User requestUser) {
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);

        User user = userService.get(username, requestUser.getPassword());


        System.out.println(username);
        System.out.println(requestUser.getPassword());



        if (null == user) {
            System.out.println("not in database");
            return new Result(400);

        } else {
            return new Result(200);
        }
    }
}
