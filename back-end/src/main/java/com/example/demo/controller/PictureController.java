package com.example.demo.controller;

import com.example.demo.Form.ModifyHeadForm1;
import com.example.demo.pojo.Goods;
import com.example.demo.pojo.User;
import com.example.demo.result.Result;
import com.example.demo.service.GoodsService;
import com.example.demo.service.UserService;
import lombok.Value;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.List;

@Controller
public class PictureController extends HttpServlet {
    //@Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER = "E:\\-\\CS309\\Project\\front-end\\src\\images\\";
    @Autowired
    UserService userService;
    @Autowired
    private GoodsService goodService;

    private String generateBytes(String path){
        File file = new File(path);//待处理的图片
        InputStream is = null;
        byte[] data = null;
        Base64.Encoder encoder = Base64.getEncoder();
//        String result = encoder.encodeToString(byteArray);
//
//        Decoder decoder = Base64.getDecoder();
//        byte[] result = decoder.decode(str);
        //读取图片字节数组
        try {
            is = new FileInputStream(file);
            data = new byte[is.available()];
            is.read(data);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("**************************不使用Base64编码处理*****************************");
//        System.out.println(new String(data));
        // 对字节数组Base64编码处理
        System.out.println("***************************使用Base64编码处理*******************************");
        //BASE64Encoder encoder = new BASE64Encoder();
        String s = encoder.encodeToString(data);// 返回Base64编码过的字节数组字符串
        System.out.println(s);
        return s;
    }


    @CrossOrigin
    @PostMapping("/api/modify_headshot")
    @ResponseBody
    public Result upload(@RequestParam(name = "file", required = false) MultipartFile file, HttpServletRequest request) {
        //public Result upload(@RequestBody ModifyHeadForm1 fileform, HttpServletRequest request) {
        int sid = (int) request.getSession().getAttribute("sid");
        User u = userService.getBySid(sid);
        //MultipartFile file=fileform.getFile();
        if (file == null) {
            System.out.println("文件为空");
            return new Result(0, "请选择要上传的图片");
        }
        if (file.getSize() > 1024 * 1024 * 10) {
            System.out.println("文件大小超过10M");
            return new Result(1, "文件大小不能大于10M");
        }
        //获取文件后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
        if (!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
            System.out.println("文件格式不正确");
            return new Result(2, "请选择jpg,jpeg,gif,png格式的图片");
        }
        String savePath = UPLOAD_FOLDER;
        File savePathFile = new File(savePath);
        if (!savePathFile.exists()) {
            //若不存在该目录，则创建目录
            savePathFile.mkdir();

        }
        //通过UUID生成唯一文件名
        //String filename = UUID.randomUUID().toString().replaceAll("-","") + "." + suffix;
        String filename = sid + "." + suffix;
        try {
            //将文件保存指定目录
            file.transferTo(new File(savePath + filename));

            InputStream inputStream = new FileInputStream(new File(savePath + filename));
            String re=generateBytes(savePath + filename);

            System.out.println(re);
            u.setHeadshot(re);

            //u.setHeadshot( filename);
            userService.save(u);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("保存文件异常");
            return new Result(3, "保存文件异常");
        }
        //返回文件名称
        return new Result(200);
    }

    @GetMapping(value = "/api/get_goods_test")
    public List<Goods> test1(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("test1");
        return goodService.findAll();
    }


    @GetMapping(value = "/api/get_picture0")
    public void query_pic(HttpServletRequest request, HttpServletResponse response) {
        try {
            System.out.println("getting pictuire...");
            int sid = 11111111;
            File pf = new File(sid + ".jpg");
            if (!pf.exists()) {
                return;
            }
            double rate = 1; //rate是压缩比率  1为原图  0.1为最模糊
            int[] results = getImgWidth(pf);
            int widthdist = 0;
            int heightdist = 0;
            if (results == null || results[0] == 0 || results[1] == 0) {
                return;
            } else {
                widthdist = (int) (results[0] * rate);
                heightdist = (int) (results[1] * rate);
            }
            Image src = ImageIO.read(pf);
            BufferedImage tag = new BufferedImage((int) widthdist, (int) heightdist,
                    BufferedImage.TYPE_INT_RGB);

            tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist, Image.SCALE_SMOOTH), 0, 0,
                    null);
            ServletOutputStream fout = response.getOutputStream();
            ImageIO.write(tag, "jpg", fout);
            fout.close();
        } catch (Exception e) {
            //异常处理
        }
    }

    public static int[] getImgWidth(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int result[] = {0, 0};
        try {
            is = new FileInputStream(file);
            src = ImageIO.read(is);
            result[0] = src.getWidth(null); // 得到源图宽
            result[1] = src.getHeight(null); // 得到源图高
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @CrossOrigin
    @GetMapping(value = "/api/get_picture1")
    @ResponseBody
    public Object export(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("getting picture");
        //获取文件需要保存的新名字
        //int sid=(int)request.getSession().getAttribute("sid");
        int sid = 11111111;
        String newFileName = request.getParameter("newFileName");
        ServletOutputStream out = null;
        FileInputStream ips = null;
        try {
            String url = UPLOAD_FOLDER + sid + "." + "png";
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
                } else {
                    return new Result(400);
                }
            } catch (IOException e) {
                System.out.println("关闭流出现异常");
                e.printStackTrace();
                return new Result(400);
            }
        }

    }
//    public void infoHe(HttpServletResponse response) {
//        InputStream in = null;
//        try{
//            //从minio文件服务器上获取图片流
//            in = minioClient.getObject(bucketName, "company/template_back_20200627160552.png");
//            response.setContentType("image/png");
//            OutputStream out = response.getOutputStream();
//            byte[] buff = new byte[100];
//            int rc = 0;
//            while ((rc = in.read(buff, 0, 100)) > 0) {
//                out.write(buff, 0, rc);
//            }
//            out.flush();
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            PropertiesConfig.globalResponse(ResultCode.FAILED);
////            return ResponseResult.success();
//        }finally {
//        }
//    }


    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @CrossOrigin
    @GetMapping(value = "/api/get_picture2")
    @ResponseBody
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String r = request.getParameter("r");
        //使用字节流读取本地图片
        ServletOutputStream out = null;
        BufferedInputStream buf = null;
        //创建一个文件对象，对应的文件就是python把词云图片生成后的路径以及对应的文件名
        File file = new File("E:\\Java\\eclipse_code\\NLP\\WebContent\\source\\wordcloud.png");
        try {
            //使用输入读取缓冲流读取一个文件输入流
            buf = new BufferedInputStream(new FileInputStream(file));
            //利用response获取一个字节流输出对象
            out = response.getOutputStream();
            //定义个数组，由于读取缓冲流中的内容
            byte[] buffer = new byte[1024];
            //while循环一直读取缓冲流中的内容到输出的对象中
            while (buf.read(buffer) != -1) {
                out.write(buffer);
            }
            //写出到请求的地方
            out.flush();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (buf != null) buf.close();
            if (out != null) out.close();
        }
        //传输结束后，删除文件，可以不删除，在生成的图片中回对此进行覆盖
        File file1 = new File("E:\\Java\\eclipse_code\\NLP\\WebContent\\source\\wordcloud.png");
        file1.delete();
        System.out.println("文件删除！");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}



