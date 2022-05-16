package com.example.demo.controller;

import com.example.demo.pojo.User;
import com.example.demo.result.Result;
import com.example.demo.service.UserService;
import com.example.demo.utils.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public User getBycurrentUser(){
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.getByName(username);
        return user;
    }



//    @GetMapping("/api/admin/user")
//    public Result listUsers() {
//        return new Result(userService.list());
//    }

//    @PutMapping("/api/admin/user/status")
//    public Result updateUserStatus(@RequestBody @Valid User requestUser) {
//        userService.updateUserStatus(requestUser);
//        return new Result(200,"用户状态更新成功");
//    }

    @PutMapping("/api/admin/user/password")
    public Result resetPassword(@RequestBody @Valid User requestUser) {
        userService.resetPassword(requestUser);
        return new Result(200,"重置密码成功");
    }

    @PutMapping("/api/admin/user")
    public Result editUser(@RequestBody @Valid User requestUser) {
        userService.editUser(requestUser);
        return new Result(200,"修改用户信息成功");
    }



    //对接收到的文件重命名，但保留原始的格式, 但6 位的随机数也可能随出完全一样的名字,有待解决
    @CrossOrigin
    @PostMapping("api/headshot")
    public String headshotUpload(MultipartFile file) throws Exception {
        String folder = "D:/sustech/ooad/project_img";
        File imageFolder = new File(folder);
        File f = new File(imageFolder, StringUtils.getRandomString(6) + file.getOriginalFilename()
                .substring(file.getOriginalFilename().length() - 4));
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        try {
            file.transferTo(f);
            String imgURL = "http://localhost:8443/api/file/" + f.getName();
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

//    public void logout() {
//        try {
//            this.clearRunAsIdentitiesInternal();
//            this.securityManager.logout(this);
//        } finally {
//            this.session = null;
//            this.principals = null;
//            this.authenticated = false;
//        }
//
//    }


//    public String profile(HttpServletRequest request) {
//        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
//        AdminUser adminUser = adminUserService.getUserDetailById(loginUserId);
//        if (adminUser == null) {
//            return "admin/login";
//        }
//        request.setAttribute("path", "profile");
//        request.setAttribute("loginUserName", adminUser.getLoginUserName());
//        request.setAttribute("nickName", adminUser.getNickName());
//        return "admin/profile";
//    }

//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request) {
//        request.getSession().removeAttribute("loginUserId");
//        request.getSession().removeAttribute("loginUser");
//        request.getSession().removeAttribute("errorMsg");
//        return "/login";
//    }
}
