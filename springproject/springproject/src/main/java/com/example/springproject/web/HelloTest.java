package com.example.springproject.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloTest {
    @RequestMapping("/hello")
    public String hello() {
        return "Hello world";
    }
}