package com.example.springproject.app;


import com.example.springproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/User")
public class UserInfoApp {
    @Autowired
    private UserService userService;
}
