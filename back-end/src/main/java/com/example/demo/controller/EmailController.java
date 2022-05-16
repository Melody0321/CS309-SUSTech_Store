package com.example.demo.controller;

import com.example.demo.pojo.User;
import com.example.demo.result.Result;
import com.example.demo.service.UserService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

@Controller
public class EmailController{
    @Autowired
    UserService userService;
    int code;
    @CrossOrigin
    @PostMapping("api/register/mail")
    @ResponseBody
    public Result register(@RequestBody User user) {
        String username = user.getUsername();

        //username = HtmlUtils.htmlEscape(username);
        user.setUsername(username);

        boolean exist = userService.isExist(username);
        if (exist) {
            System.out.println("username"+username+" already exists");
            return new Result(400,"username already exists");
        }
        String email=user.getEmail();
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        System.out.println(email);
        System.out.println(checkCode);
        code=Integer.parseInt(checkCode);

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host","smtp.qq.com");//发送邮箱服务器
        properties.setProperty("mail.smtp.port","465");//发送端口
        properties.setProperty("mail.smtp.auth","true");//是否开启权限控制
        properties.setProperty("mail.debug","true");//true 打印信息到控制台
        properties.setProperty("mail.transport","smtp");//发送的协议是简单的邮件传输协议
        properties.setProperty("mail.smtp.ssl.enable","true");
        //建立两点之间的链接
        System.out.println("执行了2");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("1141574719@qq.com","fvkqibwaxgxkbage");
            }
        });
        System.out.println("执行了3");
        //创建邮件对象
        Message message = new MimeMessage(session);
        //设置发件人
        try {
            message.setFrom(new InternetAddress("1141574719@qq.com"));

            //设置收件人
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(email));//收件人
            //设置主题
            message.setSubject("验证码");
            //设置邮件正文  第二个参数是邮件发送的类型
            message.setContent(checkCode,"text/html;charset=UTF-8");
            //发送一封邮件
            Transport transport = session.getTransport();
            transport.connect("1141574719@qq.com","fvkqibwaxgxkbage");
            Transport.send(message);
            System.out.println("发送完毕 查看验证码是否和注册页面的输入一致");
        } catch (MessagingException e) {
            System.out.println("发送出错");
            e.printStackTrace();
            return new Result(400,"邮箱不正确！");
        }finally {

        }

        System.out.println("EmailController finish");
        //userService.add(user);

        return new Result(200,"success");
    }
    public void setCheckCode(int checkCode){
        code=checkCode;
    }
    public int getCheckCode(){return code;}


    public Result inform_customer0(int sid,String goods) {
        //int sid = (int) request.getSession().getAttribute("sid");
        User u = userService.getBySid(sid);
        String email=u.getEmail();

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host","smtp.qq.com");//发送邮箱服务器
        properties.setProperty("mail.smtp.port","465");//发送端口
        properties.setProperty("mail.smtp.auth","true");//是否开启权限控制
        properties.setProperty("mail.debug","true");//true 打印信息到控制台
        properties.setProperty("mail.transport","smtp");//发送的协议是简单的邮件传输协议
        properties.setProperty("mail.smtp.ssl.enable","true");
        //建立两点之间的链接
        System.out.println("执行了2");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("1141574719@qq.com","fvkqibwaxgxkbage");
            }
        });
        System.out.println("执行了3");
        //创建邮件对象
        Message message = new MimeMessage(session);
        //设置发件人
        try {
            message.setFrom(new InternetAddress("1141574719@qq.com"));

            //设置收件人
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(email));//收件人
            //设置主题
            message.setSubject("购买成功");
            //设置邮件正文  第二个参数是邮件发送的类型
            message.setContent("恭喜你购买商品"+goods+"成功","text/html;charset=UTF-8");
            //发送一封邮件
            Transport transport = session.getTransport();
            transport.connect("1141574719@qq.com","fvkqibwaxgxkbage");
            Transport.send(message);
            System.out.println("发送完毕 查看验证码是否和注册页面的输入一致");
        } catch (MessagingException e) {
            System.out.println("发送出错");
            e.printStackTrace();
            return new Result(400,"邮箱不正确！");
        }finally {

        }

        System.out.println("EmailController finish");
        //userService.add(user);

        return new Result(200,"success");
    }

    public Result inform_seller0(int sid,String goods) {
        //int sid = (int) request.getSession().getAttribute("sid");
        User u = userService.getBySid(sid);
        String email=u.getEmail();

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host","smtp.qq.com");//发送邮箱服务器
        properties.setProperty("mail.smtp.port","465");//发送端口
        properties.setProperty("mail.smtp.auth","true");//是否开启权限控制
        properties.setProperty("mail.debug","true");//true 打印信息到控制台
        properties.setProperty("mail.transport","smtp");//发送的协议是简单的邮件传输协议
        properties.setProperty("mail.smtp.ssl.enable","true");
        //建立两点之间的链接
        System.out.println("执行了2");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("1141574719@qq.com","fvkqibwaxgxkbage");
            }
        });
        System.out.println("执行了3");
        //创建邮件对象
        Message message = new MimeMessage(session);
        //设置发件人
        try {
            message.setFrom(new InternetAddress("1141574719@qq.com"));

            //设置收件人
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(email));//收件人
            //设置主题
            message.setSubject("出售成功");
            //设置邮件正文  第二个参数是邮件发送的类型
            message.setContent("恭喜你的商品"+goods+"出售成功","text/html;charset=UTF-8");
            //发送一封邮件
            Transport transport = session.getTransport();
            transport.connect("1141574719@qq.com","fvkqibwaxgxkbage");
            Transport.send(message);
            System.out.println("发送完毕 查看验证码是否和注册页面的输入一致");
        } catch (MessagingException e) {
            System.out.println("发送出错");
            e.printStackTrace();
            return new Result(400,"邮箱不正确！");
        }finally {

        }

        System.out.println("EmailController finish");
        //userService.add(user);

        return new Result(200,"success");
    }


    public Result inform1(int sid,String goods_name,long price,int number) {
        //int sid = (int) request.getSession().getAttribute("sid");
        User u = userService.getBySid(sid);
        String email=u.getEmail();

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host","smtp.qq.com");//发送邮箱服务器
        properties.setProperty("mail.smtp.port","465");//发送端口
        properties.setProperty("mail.smtp.auth","true");//是否开启权限控制
        properties.setProperty("mail.debug","true");//true 打印信息到控制台
        properties.setProperty("mail.transport","smtp");//发送的协议是简单的邮件传输协议
        properties.setProperty("mail.smtp.ssl.enable","true");
        //建立两点之间的链接
        System.out.println("执行了2");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("1141574719@qq.com","fvkqibwaxgxkbage");
            }
        });
        System.out.println("执行了3");
        //创建邮件对象
        Message message = new MimeMessage(session);
        //设置发件人
        try {
            message.setFrom(new InternetAddress("1141574719@qq.com"));

            //设置收件人
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(email));//收件人
            //设置主题
            message.setSubject("购买成功");
            //设置邮件正文  第二个参数是邮件发送的类型
            message.setContent("恭喜你购买商品"+goods_name+"成功，商品数量为"+number+"，共消费"+price+"元。","text/html;charset=UTF-8");
            //发送一封邮件
            Transport transport = session.getTransport();
            transport.connect("1141574719@qq.com","fvkqibwaxgxkbage");
            Transport.send(message);
            System.out.println("发送完毕 查看验证码是否和注册页面的输入一致");
        } catch (MessagingException e) {
            System.out.println("发送出错");
            e.printStackTrace();
            return new Result(400,"邮箱不正确！");
        }finally {

        }

        System.out.println("EmailController finish");
        //userService.add(user);

        return new Result(200,"success");
    }

    public Result inform_seller(int sid,String goods_name,long price,int number) {
        //int sid = (int) request.getSession().getAttribute("sid");
        User u = userService.getBySid(sid);
        String email=u.getEmail();

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host","smtp.qq.com");//发送邮箱服务器
        properties.setProperty("mail.smtp.port","465");//发送端口
        properties.setProperty("mail.smtp.auth","true");//是否开启权限控制
        properties.setProperty("mail.debug","true");//true 打印信息到控制台
        properties.setProperty("mail.transport","smtp");//发送的协议是简单的邮件传输协议
        properties.setProperty("mail.smtp.ssl.enable","true");
        //建立两点之间的链接
        System.out.println("执行了2");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("1141574719@qq.com","fvkqibwaxgxkbage");
            }
        });
        System.out.println("执行了3");
        //创建邮件对象
        Message message = new MimeMessage(session);
        //设置发件人
        try {
            message.setFrom(new InternetAddress("1141574719@qq.com"));

            //设置收件人
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(email));//收件人
            //设置主题
            message.setSubject("出售成功");
            //设置邮件正文  第二个参数是邮件发送的类型
            message.setContent("恭喜您的商品"+goods_name+"成功卖出"+number+"件，交易额"+price+"元。","text/html;charset=UTF-8");
            //发送一封邮件
            Transport transport = session.getTransport();
            transport.connect("1141574719@qq.com","fvkqibwaxgxkbage");
            Transport.send(message);
            System.out.println("发送完毕 查看验证码是否和注册页面的输入一致");
        } catch (MessagingException e) {
            System.out.println("发送出错");
            e.printStackTrace();
            return new Result(400,"邮箱不正确！");
        }finally {

        }

        System.out.println("EmailController finish");
        //userService.add(user);

        return new Result(200,"success");
    }
}