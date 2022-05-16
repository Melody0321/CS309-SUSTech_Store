package com.example.demo.controller;

import com.example.demo.pojo.Goods;
import com.example.demo.pojo.Goods_img;
import com.example.demo.result.Result;
import com.example.demo.service.CommentService;
import com.example.demo.service.GoodsService;
import com.example.demo.service.Goods_ImgService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
public class GoodsImgController {
    private String UPLOAD_FOLDER = "E:\\-\\CS309\\Project\\front-end\\src\\images\\";
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private Goods_ImgService goods_imgService;

//    @GetMapping
//    public List<Goods_img> getImgs()
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

    @GetMapping("/api/getGoodsPicture/{id}")//传的是goods_id
    public List<String> getImgs(@PathVariable("id") String id,
                          HttpServletRequest request){
        long goods_id=Long.parseLong(id);
        List<Goods_img> goods_imgs=goods_imgService.getByGoods_id(goods_id);
        ArrayList<String> re=new ArrayList<>();
        int count=0;
        for (Goods_img gi:goods_imgs
             ) {
            if(!gi.getDeleted()){
                re.add(gi.getPicture());
                count+=1;
            }
            if (count>=5)
                break;
        }
        return re;
    }
    @CrossOrigin
    @PostMapping("/api/addGoodsPicture/{id}")//传的是goods_id
    @ResponseBody
    public Result addOne(//@RequestParam String goods_name,
                         //@RequestParam String detail,
                         //@RequestParam String tag,
                         //@RequestParam int price,
                         @RequestParam(name = "file", required = false) MultipartFile file,
                         @PathVariable("id") String id,
                         HttpServletRequest request){
        System.out.println("start posting goods");

        //System.out.println(goods_name+" "+detail+" "+tag+" "+price+" "+rest_num);

        int sid= (int) request.getSession().getAttribute("sid");
        long goods_id=Long.parseLong(id);
        //Goods g=goodsService.findbyGoodsId(goods_id);
        //int sid=11111111;
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
        System.out.println("current count of goods table is:"+goodsService.getCount());
        String filename = goodsService.getCount() + "." + suffix;
        try {
            //将文件保存指定目录
            file.transferTo(new File(savePath + filename));
            //新加的


            InputStream inputStream = new FileInputStream(new File(savePath + filename));
            String re=generateBytes(savePath + filename);

            System.out.println("generate bit stream");
            //Goods goods=new Goods(sid,goods_name,detail,tag,price,re,rest_num);
            Goods_img g=new Goods_img(goods_id,re,false);
            Goods goods=goodsService.findbyGoodsId(goods_id);
            goods_imgService.save(g);
            if(goods.getPicture()==null){
                goods.setPicture(re);
                System.out.println(goods.toString());
                goodsService.save(goods);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("保存文件异常");
            return new Result(3, "保存文件异常");
        }
        //返回文件名称
        return new Result(200);

    }

    @CrossOrigin
    @PostMapping("/api/updateGoodsPicture/{id}")//传的是goods_img_id
    @ResponseBody
    public Result updateOne(
                         @RequestParam(name = "file", required = false) MultipartFile file,
                         @PathVariable("id") String id,
                         HttpServletRequest request){
        System.out.println("start updating goods picture");

        //System.out.println(goods_name+" "+detail+" "+tag+" "+price+" "+rest_num);

        int sid= (int) request.getSession().getAttribute("sid");
        long goods_img_id=Long.parseLong(id);
        //Goods g=goodsService.findbyGoodsId(goods_id);
        //int sid=11111111;
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
        System.out.println("current count of goods table is:"+goodsService.getCount());
        String filename = goodsService.getCount() + "." + suffix;
        try {
            //将文件保存指定目录
            file.transferTo(new File(savePath + filename));
            //新加的


            InputStream inputStream = new FileInputStream(new File(savePath + filename));
            String re=generateBytes(savePath + filename);

            System.out.println("generate bit stream");
            //Goods goods=new Goods(sid,goods_name,detail,tag,price,re,rest_num);
            Goods_img g=goods_imgService.getByGoods_img_id(goods_img_id);
            g.setPicture(re);
            goods_imgService.save(g);
            //g.setPicture(re);
            //System.out.println(goods.toString());
            //goodsService.save(g);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("保存文件异常");
            return new Result(3, "保存文件异常");
        }
        //返回文件名称
        return new Result(200);

    }

    @CrossOrigin
    @PostMapping("/api/deleteGoodsPicture/{id}")//传的是goods_img_id
    @ResponseBody
    public Result deleteOne(
                            @PathVariable("id") String id,
                            HttpServletRequest request){
        System.out.println("start delete goods picture");

        int sid= (int) request.getSession().getAttribute("sid");
        long goods_img_id=Long.parseLong(id);
        try {
            System.out.println("generate bit stream");
            //Goods goods=new Goods(sid,goods_name,detail,tag,price,re,rest_num);
            Goods_img g=goods_imgService.getByGoods_img_id(goods_img_id);
            g.setDeleted(true);
            goods_imgService.save(g);
            //g.setPicture(re);
            //System.out.println(goods.toString());
            //goodsService.save(g);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("保存文件异常");
            return new Result(3, "保存文件异常");
        }
        //返回文件名称
        return new Result(200);

    }
}
