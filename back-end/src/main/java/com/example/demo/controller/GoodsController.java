package com.example.demo.controller;



//import com.example.demo.dto.SearchGoodsDTO;
import com.example.demo.Form.*;
import com.example.demo.pojo.*;
import com.example.demo.result.Result;
import com.example.demo.service.CommentService;
import com.example.demo.service.GoodsService;
import com.example.demo.service.Goods_ImgService;
import com.example.demo.service.UserService;
import io.micrometer.core.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

@RestController
//@RequestMapping("/good")
public class GoodsController {
    //private String UPLOAD_FOLDER = "D:/sustech/ooad/project_img/";
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private Goods_ImgService goods_imgService;

    @GetMapping("/record")
    public List<Goods>findAll(){
        System.out.println(goodsService.getClass().getName());
        return goodsService.findAll();
    }

    @GetMapping("/tag/{tag}")
    public List<Goods> listByTag(@PathVariable("tag") String tag) throws Exception {
        return goodsService.findAllByTag(tag);
    }



    @GetMapping("/name/{name}")
    public Goods findByGoodsName(@PathVariable("name") String name) throws Exception {
        return goodsService.findByGoodsName(name);
    }

    @GetMapping("/id/{id}")
    public Goods findByGoodsId(@PathVariable("id") long id) throws Exception {
        return goodsService.findbyGoodsId(id);
    }
//    @GetMapping("/comment/goods_id")
//    public List<comment_record> showComment(@RequestBody GetCommentForm getCommentForm, HttpServletRequest request){
//        //Goods goods=goodsService.findbyGoodsId(id);
//        List<Comment> l=commentService.getByGoods_id(getCommentForm.getGoods_id());
//        ArrayList<comment_record> re=new ArrayList<>();
//        for (Comment c:l
//             ) {
//            User u=userService.getBySid(c.getUser_id());
//            comment_record record=new comment_record(u.getUsername(),c.getScore(),c.getComment());
//            re.add(record);
//            System.out.println(record.toString());
//        }
//        return re;
//    }
    @GetMapping("/comment/goods_id")
    public List<comment_record> showComment(@RequestParam long goods_id)throws  Exception{
        //Goods goods=goodsService.findbyGoodsId(id);
        List<Comment> l=commentService.getByGoods_id(goods_id);
        ArrayList<comment_record> re=new ArrayList<>();
        for (Comment c:l
        ) {
            User u=userService.getBySid(c.getUser_id());
            comment_record record=new comment_record(u.getUsername(),c.getScore(),c.getComment());
            re.add(record);
            System.out.println(record.toString());
        }
        return re;
    }

    @GetMapping("/comment/goods_id/test")
    public List<comment_record> showComment1(){
        //Goods goods=goodsService.findbyGoodsId(id);
        List<Comment> l=commentService.getByGoods_id(1);
        ArrayList<comment_record> re=new ArrayList<>();
        for (Comment c:l
        ) {
            User u=userService.getBySid(c.getUser_id());
            comment_record record=new comment_record(u.getUsername(),c.getScore(),c.getComment());
            re.add(record);
            System.out.println(record.toString());
        }
        return re;
    }
    @GetMapping("/top8")
    public List<goods_detail>findTop8(){
        System.out.println("find top 8");
        List<Goods> goods=goodsService.findTop8ByOrderByGoods_idAsc();
        ArrayList<goods_detail> re=new ArrayList<>();
        for (Goods g:goods
        ) {
            if(!g.isIs_deleted() && g.getRest_num()>0){
                List<Comment> l=commentService.getByGoods_id(g.getGoods_id());
                ArrayList<comment_record> re1=new ArrayList<>();
                List<Goods_img> imgs=goods_imgService.getByGoods_id(g.getGoods_id());
                ArrayList<String> img_re=new ArrayList<>();
                for (Goods_img i: imgs
                ) {
                    if(!i.getDeleted()){
                        img_re.add(i.getPicture());
                    }
                }
                for (Comment c:l
                ) {
                    User u=userService.getBySid(c.getUser_id());
                    comment_record record=new comment_record(u.getUsername(),c.getScore(),c.getComment());
                    re1.add(record);
                    System.out.println(record.toString());
                }
                goods_detail gd=new goods_detail(g,re1,img_re);
                re.add(gd);
            }

        }
        return re;
    }
    @GetMapping("/info/test")
    public List<goods_detail> findByGoodsInfoTest()throws  Exception{

        System.out.println("info function called.");
        List<Goods> all= goodsService.findAll();
        ArrayList<goods_detail> re=new ArrayList<>();
        for (Goods g:all
        ) {
            if(!g.isIs_deleted() && g.getRest_num()>0){
                List<Comment> l=commentService.getByGoods_id(g.getGoods_id());
                ArrayList<comment_record> re1=new ArrayList<>();
                List<Goods_img> imgs=goods_imgService.getByGoods_id(g.getGoods_id());
                ArrayList<String> img_re=new ArrayList<>();
                for (Goods_img i: imgs
                ) {
                    if(!i.getDeleted()){
                        img_re.add(i.getPicture());
                    }
                }
                for (Comment c:l
                ) {
                    User u=userService.getBySid(c.getUser_id());
                    comment_record record=new comment_record(u.getUsername(),c.getScore(),c.getComment());
                    re1.add(record);
                    System.out.println(record.toString());
                }
                goods_detail gd=new goods_detail(g,re1,img_re);
                re.add(gd);
            }

        }
        return re;
    }
    @GetMapping("/info")
    public List<goods_detail> findByGoodsInfo(@RequestParam String tag,
                                              @RequestParam String name,
                                              @RequestParam int lowest,
                                              @RequestParam int highest,
                                              @RequestParam int number)throws  Exception{

        System.out.println("info function called.");
        List<Goods> all= goodsService.findByGoodsInfo(tag,name,lowest,highest,number);
        ArrayList<goods_detail> re=new ArrayList<>();
        for (Goods g:all
        ) {
            if(!g.isIs_deleted() && g.getRest_num()>0){
                List<Comment> l=commentService.getByGoods_id(g.getGoods_id());
                ArrayList<comment_record> re1=new ArrayList<>();
                List<Goods_img> imgs=goods_imgService.getByGoods_id(g.getGoods_id());
                ArrayList<String> img_re=new ArrayList<>();
                for (Goods_img i: imgs
                     ) {
                    if(!i.getDeleted()){
                        img_re.add(i.getPicture());
                    }
                }
                for (Comment c:l
                ) {
                    User u=userService.getBySid(c.getUser_id());
                    comment_record record=new comment_record(u.getUsername(),c.getScore(),c.getComment());
                    re1.add(record);
                    System.out.println(record.toString());
                }
                goods_detail gd=new goods_detail(g,re1,img_re);
                re.add(gd);
            }

        }
        return re;
    }


//    @GetMapping("/info")
//    public List<goods_detail> findByGoodsInfo(@RequestParam String tag,
//                                       @RequestParam String name,
//                                       @RequestParam int lowest,
//                                       @RequestParam int highest,
//                                       @RequestParam int number)throws  Exception{
//
//        System.out.println("info function called.");
//        List<Goods> all= goodsService.findByGoodsInfo(tag,name,lowest,highest,number);
//        ArrayList<goods_detail> re=new ArrayList<>();
//        for (Goods g:all
//        ) {
//            if(!g.isIs_deleted() && g.getRest_num()>0){
//                List<Comment> l=commentService.getByGoods_id(g.getGoods_id());
//                ArrayList<comment_record> re1=new ArrayList<>();
//                for (Comment c:l
//                ) {
//                    User u=userService.getBySid(c.getUser_id());
//                    comment_record record=new comment_record(u.getUsername(),c.getScore(),c.getComment());
//                    re1.add(record);
//                    System.out.println(record.toString());
//                }
//                goods_detail gd=new goods_detail(g,re1);
//                re.add(gd);
//            }
//
//        }
//        return re;
//    }



//    @GetMapping("/info")
//    public List<Goods> findByGoodsInfo(@RequestParam String tag,
//                                       @RequestParam String name,
//                                       @RequestParam int lowest,
//                                       @RequestParam int highest,
//                                       @RequestParam int number)throws  Exception{
//
//        System.out.println("info function called.");
//        List<Goods> all= goodsService.findByGoodsInfo(tag,name,lowest,highest,number);
//        ArrayList<Goods> re=new ArrayList<>();
//        for (Goods g:all
//             ) {
//            if(!g.isIs_deleted() && g.getRest_num()>0)
//                re.add(g);
//        }
//        return re;
//    }



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




    @PostMapping("/api/addOne")
    public Result addOne(@RequestBody PostGoodsForm form, HttpServletRequest request){
        System.out.println(form.toString());
        System.out.println("start posting goods");
//        System.out.println("start"+goods.getTag()+"end");
//        System.out.println(goods.getTag() ==null);
        int sid= (int) request.getSession().getAttribute("sid");
        User user=userService.getBySid(sid);
        if(user.getCredit()>0) {
            Goods goods = new Goods(sid, form.goods_name, form.detail, form.tag, form.price, form.rest_num);
//        System.out.println(sid);
//        goods.setSeller_id(sid);
            System.out.println(goods.toString());
            goodsService.save(goods);

            return new Result(200);
        }
        else
            return new Result(400);


    }

@PostMapping("/api/addOne/test")
public void addOne_test(@RequestParam String goods_name,
                     @RequestParam String detail,
                     @RequestParam String tag,
                     @RequestParam int price,
                     @RequestParam String picture,
                     @RequestParam int rest_num
                     ){
    System.out.println("add one testing");
}

    @PostMapping("/api/addOne/test1")
    public Result addOne1(@RequestParam String goods_name){
        System.out.println(goods_name);
        return new Result(200);
    }


//    @CrossOrigin
//    @PostMapping("/api/addGoodsPicture/{id}")
//    @ResponseBody
//    public Result addOne(//@RequestParam String goods_name,
//                         //@RequestParam String detail,
//                         //@RequestParam String tag,
//                         //@RequestParam int price,
//                         @RequestParam(name = "file", required = false) MultipartFile file,
//                         @PathVariable("id") String id,
//                         HttpServletRequest request){
//        System.out.println("start posting goods");
//
//        //System.out.println(goods_name+" "+detail+" "+tag+" "+price+" "+rest_num);
//
//        int sid= (int) request.getSession().getAttribute("sid");
//        long goods_id=Long.parseLong(id);
//        Goods g=goodsService.findbyGoodsId(goods_id);
//        //int sid=11111111;
//        if (file == null) {
//            System.out.println("文件为空");
//            return new Result(0, "请选择要上传的图片");
//        }
//        if (file.getSize() > 1024 * 1024 * 10) {
//            System.out.println("文件大小超过10M");
//            return new Result(1, "文件大小不能大于10M");
//        }
//        //获取文件后缀
//        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
//        if (!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
//            System.out.println("文件格式不正确");
//            return new Result(2, "请选择jpg,jpeg,gif,png格式的图片");
//        }
//        String savePath = UPLOAD_FOLDER;
//        File savePathFile = new File(savePath);
//        if (!savePathFile.exists()) {
//            //若不存在该目录，则创建目录
//            savePathFile.mkdir();
//
//        }
//        //通过UUID生成唯一文件名
//
//        //String filename = UUID.randomUUID().toString().replaceAll("-","") + "." + suffix;
//        System.out.println("current count of goods table is:"+goodsService.getCount());
//        String filename = goodsService.getCount() + "." + suffix;
//        try {
//            //将文件保存指定目录
//            file.transferTo(new File(savePath + filename));
//            //新加的
//
//
//            InputStream inputStream = new FileInputStream(new File(savePath + filename));
//            String re=generateBytes(savePath + filename);
//
//            System.out.println(re);
//            //Goods goods=new Goods(sid,goods_name,detail,tag,price,re,rest_num);
//            g.setPicture(re);
//            //System.out.println(goods.toString());
//            goodsService.save(g);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("保存文件异常");
//            return new Result(3, "保存文件异常");
//        }
//        //返回文件名称
//        return new Result(200);
//
//
//
//    }

    @CrossOrigin
    @PutMapping("/update")
    public Goods update(@RequestBody Goods goods, HttpServletRequest request){
        System.out.println("start"+goods.getGoods_id()+"end");
//        int sid= (int) request.getSession().getAttribute("sid");
//        goods.setSeller_id(sid);

        return  goodsService.save(goods);
    }

    @CrossOrigin
    @PostMapping("/api/modify_goods/name")
    @ResponseBody
    public Result modifyinfo(@RequestBody GoodsName form, HttpServletRequest request){
        long id=form.getGoods_id();
        Goods g=goodsService.findbyGoodsId(id);
        g.setGoods_name(form.getGoods_name());

        System.out.println("new goods name: "+ g.getGoods_name());
        goodsService.save(g);
        return new Result(200);
    }
    @CrossOrigin
    @PostMapping("/api/modify_goods/detail")
    @ResponseBody
    public Result modifyinfo1(@RequestBody GoodsDetail form, HttpServletRequest request){
        long id=form.getGoods_id();
        Goods g=goodsService.findbyGoodsId(id);
        g.setDetail(form.getDetail());

        System.out.println("new goods detail: "+ g.getDetail());
        goodsService.save(g);
        return new Result(200);
    }
    @CrossOrigin
    @PostMapping("/api/modify_goods/tag")
    @ResponseBody
    public Result modifyinfo2(@RequestBody GoodsTag form, HttpServletRequest request){
        long id=form.getGoods_id();
        Goods g=goodsService.findbyGoodsId(id);
        g.setTag(form.getTag());

        System.out.println("new goods tag: "+ g.getTag());
        goodsService.save(g);
        return new Result(200);
    }
    @CrossOrigin
    @PostMapping("/api/modify_goods/delete")
    @ResponseBody
    public Result modifyinfo3(@RequestBody GoodsDelete form, HttpServletRequest request){
        long id=form.getGoods_id();
        Goods g=goodsService.findbyGoodsId(id);
        g.setIs_deleted(true);

        System.out.println("The goods is deleted ");
        goodsService.save(g);
        return new Result(200);
    }
    @CrossOrigin
    @PostMapping("/api/modify_goods/price")
    @ResponseBody
    public Result modifyinfo4(@RequestBody GoodsPrice form, HttpServletRequest request){
        long id=form.getGoods_id();
        Goods g=goodsService.findbyGoodsId(id);
        g.setPrice(form.getPrice());

        System.out.println("new goods price: "+ g.getPrice());
        goodsService.save(g);
        return new Result(200);
    }
    @CrossOrigin
    @PostMapping("/api/modify_goods/rest")
    @ResponseBody
    public Result modifyinfo5(@RequestBody GoodsRestNum form, HttpServletRequest request){
        long id=form.getGoods_id();
        Goods g=goodsService.findbyGoodsId(id);
        g.setRest_num(form.getRest_num());

        System.out.println("new goods rest number: "+ g.getRest_num());
        goodsService.save(g);
        return new Result(200);
    }

    static class goods_detail{
        Goods goods;
        List<comment_record> comments;
        List<String> imgs;

        public goods_detail(Goods goods, List<comment_record> comments,List<String> imgs) {
            this.goods = goods;
            this.comments = comments;
            this.imgs=imgs;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }

        public goods_detail(){}

        public Goods getGoods() {
            return goods;
        }

        public void setGoods(Goods goods) {
            this.goods = goods;
        }

        public List<comment_record> getComments() {
            return comments;
        }

        public void setComments(List<comment_record> comments) {
            this.comments = comments;
        }
    }
//    static class goods_detail{
//        Goods goods;
//        List<comment_record> comments;
//
//        public goods_detail(Goods goods, List<comment_record> comments) {
//            this.goods = goods;
//            this.comments = comments;
//        }
//        public goods_detail(){}
//
//        public Goods getGoods() {
//            return goods;
//        }
//
//        public void setGoods(Goods goods) {
//            this.goods = goods;
//        }
//
//        public List<comment_record> getComments() {
//            return comments;
//        }
//
//        public void setComments(List<comment_record> comments) {
//            this.comments = comments;
//        }
//    }
    static class comment_record{
        String username;
        int score;
        String comment;
        public comment_record(){}
        public comment_record(String username, int score, String comment) {
            this.username = username;
            this.score = score;
            this.comment = comment;
        }

        @Override
        public String toString() {
            return "comment_record{" +
                    "username='" + username + '\'' +
                    ", score=" + score +
                    ", comment='" + comment + '\'' +
                    '}';
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }
}
