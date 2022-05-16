package com.example.demo.controller;

import com.example.demo.pojo.User;
import com.example.demo.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserFindIDServlet extends HttpServlet {
    UserService userService;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User users = null;
        HttpSession session = request.getSession(false);
        String userId = String.valueOf(session.getAttribute("userId"));
        users = userService.getBySid(Integer.parseInt(userId));
        request.setAttribute("user",users);
        request.getRequestDispatcher("/accountId.jsp").forward(request,response);
    }
}

