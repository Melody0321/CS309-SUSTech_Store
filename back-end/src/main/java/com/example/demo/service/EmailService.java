package com.example.demo.service;//package com.example.demo.service;
//
//import com.example.demo.mail.SendEmail;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.regex.Pattern;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//
//
//@SuppressWarnings("serial")
//@WebServlet(urlPatterns = {"api/register"})//(urlPatterns={"/EmailServlet"})
//public class EmailService extends HttpServlet {
//    private String email;  // receiver
//    private String vCode;  // sended vcode
//    private String vCodeReceive;  // the input vcode
//    private String method;  // 要接收的方法
//    private PrintWriter out;  // 输出流
//    private SendEmail emailUtil = SendEmail.instance;
//    public EmailService() {
//        // TODO Auto-generated constructor stub
//        System.out.println("初始化");
//
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // TODO Auto-generated method stub
//        //doPost(req, resp);
//        email = req.getParameter("email");
//        vCodeReceive = req.getParameter("vcode");
//        method = req.getParameter("method");
//
//        System.out.println("receiver's email:"+email);
//
//        // TODO Auto-generated method stub
//        resp.setContentType("text/html");
//        //语言编码
//        req.setCharacterEncoding("utf-8");
//        resp.setCharacterEncoding("utf-8");
//
//        out = resp.getWriter();
//        // 获取来自前端的参数
//
//
//        switch (method) {
//            case "getVCode":
//                mGetVCode();
//                break;
//            case "verify":
//                mVerify();
//                break;
//            default:
//                break;
//        }
//
//        out.flush();
//        out.close();
//    }
//
//    /*
//     * 在此实现servlet
//     */
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // TODO Auto-generated method stub
////        resp.setContentType("text/html");
////        //语言编码
////        req.setCharacterEncoding("utf-8");
////        resp.setCharacterEncoding("utf-8");
////
////        out = resp.getWriter();
////        // 获取来自前端的参数
////
////
////        switch (method) {
////            case "getVCode":
////                mGetVCode();
////                break;
////            case "verify":
////                mVerify();
////                break;
////            default:
////                break;
////        }
////
////        out.flush();
////        out.close();
//        doGet(req,resp);
//    }
//
//    /*
//     * 产生验证码，并发送邮件
//     */
//    private void mGetVCode() {
//        // TODO Auto-generated method stub
//        if(!isEmail(email)) {  // 邮箱不正确
//            out.print(-1);
//            return;
//        }
//        try {
//            emailUtil.sendEmail(email);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        vCode = emailUtil.getVCode();
//        System.out.println("验证码为：" + vCode);
//        out.print(1);
//    }
//
//    /*
//     * 邮箱正确性检测
//     * @param 邮箱字符串
//     * @return true/false
//     */
//    private boolean isEmail(String email) {
//        if(email.length() == 0 || email == null) {
//            return false;
//        }
//        // 正则表达式验证邮箱
//        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
//        return pattern.matcher(email).matches();
//    }
//
//    /*
//     * 验证码验证
//     */
//    private void mVerify() {
//        // TODO Auto-generated method stub
//        if(vCode.equals(vCodeReceive)) {
//            out.print(1);
//        }
//        else {
//            out.print(-1);
//        }
//    }
//    @Override
//    public void destroy() {
//        // TODO Auto-generated method stub
//        super.destroy();
//    }
//
//    @Override
//    public void init() throws ServletException {
//        // TODO Auto-generated method stub
//        super.init();
//    }
//}
