package com.example.demo.controller;

import com.example.demo.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;


@Controller
public class test {
//    public static void main(String args[]) {
//
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        Time time = new Time(System.currentTimeMillis());
//        System.out.println(timestamp);
//        System.out.println(time);
//    }
    private String UPLOAD_FOLDER="D:/Computer_science/2021fall/OOAD/learnweb/demo1113/demo/src/main/resources/project_img/";
    @RequestMapping(value ="/api/get_picture")
    @ResponseBody
    public Object export(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取文件需要保存的新名字
        int sid=(int)request.getSession().getAttribute("sid");
        String newFileName = request.getParameter("newFileName");
        ServletOutputStream out = null;
        FileInputStream ips = null;
        try {
            String url = UPLOAD_FOLDER+ sid + "." + "png";
            System.out.println(url);
            //获取文件存放的路径
            //原本文件存放的路径
            File file = new File(url);
            if (!file.exists()) {
                //如果文件不存在就跳出
                System.out.println("文件不存在");
                return new Result(400);
            }
            ips = new FileInputStream(file);
            response.setContentType("multipart/form-data");
            //为下载文件设置名字
            response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(newFileName.getBytes("UTF-8"), "ISO8859-1") + "\"");
            //获取响应的输出流
            out = response.getOutputStream();
            //读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = ips.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null && ips != null) {
                    out.close();
                    ips.close();
                    return new Result(200);
                }
                else{
                    return new Result(400);
                }
            } catch (IOException e) {
                System.out.println("关闭流出现异常");
                e.printStackTrace();
                return new Result(400);
            }
        }

    }


}
