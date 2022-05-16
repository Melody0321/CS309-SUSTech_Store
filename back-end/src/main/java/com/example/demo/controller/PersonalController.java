package com.example.demo.controller;


import com.example.demo.Form.*;
import com.example.demo.pojo.*;
import com.example.demo.result.Result;
import com.example.demo.service.*;
import com.example.demo.utils.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@RestController
//@RequestMapping("/good")
public class PersonalController {
    @Autowired
    private GoodsService goodService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private Goods_ImgService goods_imgService;

//    @GetMapping("/record")
//    public List<Goods>findAll(){
//        System.out.println(goodService.getClass().getName());
//        return goodService.findAll();
//    }
    @GetMapping("/personal/11111111")
    public User findUser(HttpServletRequest request, HttpServletResponse response){
        int sid= (int) request.getSession().getAttribute("sid");
        User u=userService.getBySid(sid);
        System.out.println(u.toString());
        return u;
    }

    @GetMapping("/purchase_record")   //已买到的商品
    public List<record1> findBuy(HttpServletRequest request){
        int id= (int) request.getSession().getAttribute("sid");
        //int id=11111111;
        List<Transaction> l= transactionService.getByCustomerId(id);
        ArrayList<record1> re=new ArrayList<>();
        for (Transaction t:l
             ) {
            Goods g=goodService.findbyGoodsId(t.getGoods_id());
            Comment c=commentService.getByUserIdAndGoodsId(id,g.getGoods_id());
            String comment="";
            if (c!=null){
                comment=c.getComment();
            }
            record1 r=new record1(t.getTransaction_id(),t.getTime(),t.getCustomer_id(),t.getSeller_id(),t.getGoods_num(),g.getGoods_name(),g.getDetail(),g.getPrice(),t.getTotal_price(),comment);
            re.add(r);
        }
        return re;
    }
//    @GetMapping("/purchase_record")   //已买到的商品
//    public List<Goods> findBuy(HttpServletRequest request){
//        //int id= (int) request.getSession().getAttribute("sid");
//        int id=11111111;
//        List<Long> l=transactionService.getByCustomerId(id);
//
//        ArrayList<Goods> result=new ArrayList<>();
//        for (Long i:l
//        ) {
//            System.out.println("goods's id is :"+i);
//            result.add(goodService.findbyGoodsId(i));
//        }
//        return result;
//    }

    @GetMapping("/selling_record/test")    //发布的正在卖的商品
    public List<selling_record> findSellingTest(HttpServletRequest request){
        //int id= (int) request.getSession().getAttribute("sid");
        int id=11111111;
        List<Goods> all= goodService.findBySellerId(id);
        ArrayList<selling_record> re=new ArrayList<>();
        for (Goods g:all
        ) {
            if (!g.isIs_deleted()){
                List<Goods_img> goods_imgs=goods_imgService.getByGoods_id(g.getGoods_id());
                ArrayList<String> re2=new ArrayList<>();
                int count=0;
                for (Goods_img gi:goods_imgs
                ) {
                    if(!gi.getDeleted()){
                        re2.add(gi.getPicture());
                        count+=1;
                    }
                    if (count>=5)
                        break;
                }
                selling_record s=new selling_record(g,re2);
                re.add(s);
            }

        }
        return re;
    }

    @GetMapping("/selling_record")    //发布的正在卖的商品
    public List<selling_record> findSelling(HttpServletRequest request){
        int id= (int) request.getSession().getAttribute("sid");
        //int id=11111111;
        List<Goods> all= goodService.findBySellerId(id);
        ArrayList<selling_record> re=new ArrayList<>();
        for (Goods g:all
             ) {
            if (!g.isIs_deleted()){
                List<Goods_img> goods_imgs=goods_imgService.getByGoods_id(g.getGoods_id());
                ArrayList<String> re2=new ArrayList<>();
                int count=0;
                for (Goods_img gi:goods_imgs
                ) {
                    if(!gi.getDeleted()){
                        re2.add(gi.getPicture());
                        count+=1;
                    }
                    if (count>=5)
                        break;
                }
                selling_record s=new selling_record(g,re2);
                re.add(s);
            }

        }
        return re;
    }
    static class selling_record{
        Goods goods;
        List<String> imgs;
        public selling_record(){

        }
        public selling_record(Goods goods, List<String> imgs) {
            this.goods = goods;
            this.imgs = imgs;
        }

        public Goods getGoods() {
            return goods;
        }

        public void setGoods(Goods goods) {
            this.goods = goods;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }
    }

    @GetMapping("/posted_record/test")  //发布的求购
    public List<Post> findPostedt(HttpServletRequest request){

        int sid= 11111111;
        User u=userService.getBySid(sid);
        List<Post> all= postService.getByUserId(sid);
        ArrayList<Post> re=new ArrayList<>();
        for (Post p:all
        ) {
            System.out.println(p.toString());
            if(!p.isIs_deleted())
                re.add(p);
        }
        return re;
    }


    @GetMapping("/posted_record")  //发布的求购
    public List<Post> findPosted(HttpServletRequest request){

        int sid= (int) request.getSession().getAttribute("sid");
        User u=userService.getBySid(sid);
        List<Post> all= postService.getByUserId(sid);
        ArrayList<Post> re=new ArrayList<>();
        for (Post p:all
        ) {
            System.out.println(p.toString());
            if(!p.isIs_deleted())
                re.add(p);
        }
        return re;
    }
    @GetMapping("/goods/{id}")
    public Goods findGoods(@PathVariable("id") long goods_id,HttpServletRequest request){

        //int id= (int) request.getSession().getAttribute("sid");
        return goodService.findbyGoodsId(goods_id);
    }
    @GetMapping("/sales/test")
    public List<Transaction> findSales0(HttpServletRequest request){
        //int id= (int) request.getSession().getAttribute("sid");
        int id=11111111;
        return transactionService.getBySellerId(id);
    }
    @GetMapping("/purchase/test")
    public List<record> findSales1(HttpServletRequest request){
        //int id= (int) request.getSession().getAttribute("sid");
        int id=11111111;
//        return transactionService.getByCustomerId(id);
        ArrayList<String> result=new ArrayList<>();

        //int id=11111111;
        List<Transaction>  l1=transactionService.getBySellerId(id);
        ArrayList<record> re=new ArrayList<>();
        for (Transaction t:l1
        ) {
            Goods g=goodService.findbyGoodsId(t.getGoods_id());
            record r=new record(t.getTransaction_id(),t.getTime(),t.getCustomer_id(),t.getSeller_id(),t.getGoods_num(),g.getGoods_name(),g.getDetail(),g.getPrice(),t.getTotal_price());
            re.add(r);
        }
        return re;
    }

    @GetMapping("/sales_record")  //已经卖出去的商品
    public List<record> findSales(HttpServletRequest request){
        int id= (int) request.getSession().getAttribute("sid");
        ArrayList<String> result=new ArrayList<>();

        //int id=11111111;
        List<Transaction>  l1=transactionService.getBySellerId(id);
        ArrayList<record> re=new ArrayList<>();
        for (Transaction t:l1
             ) {
            Goods g=goodService.findbyGoodsId(t.getGoods_id());
            record r=new record(t.getTransaction_id(),t.getTime(),t.getCustomer_id(),t.getSeller_id(),t.getGoods_num(),g.getGoods_name(),g.getDetail(),g.getPrice(),t.getTotal_price());
            re.add(r);
        }
        return re;
    }
//    @GetMapping("/sales_record")  //已经卖出去的商品
//    public List<Goods> findSales(HttpServletRequest request){
//        int id= (int) request.getSession().getAttribute("sid");
//        List<Long> l=transactionService.getBySellerId(id);
//
//        ArrayList<Goods> result=new ArrayList<>();
//        for (Long i:l
//        ) {
//            System.out.println("goods's id is :"+i);
//            result.add(goodService.findbyGoodsId(i));
//        }
//        return result;
//    }
//    @GetMapping("/purchase")
//    public List<Goods> findPurchase(HttpServletRequest request){
//
//        int id= (int) request.getSession().getAttribute("sid");
//        return transactionService.getGoodsByCustomerId(id);
//    }

    @CrossOrigin
    @PostMapping("/api/recharge")
    @ResponseBody
    public Result recharge(@RequestBody RechargeForm form, HttpServletRequest request, HttpServletResponse response){
        int sid= (int) request.getSession().getAttribute("sid");
        System.out.println(sid+"is recharging");
        User u=userService.getBySid(sid);

        String username=u.getUsername();
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,form.getPassword());
        try{
            subject.login(usernamePasswordToken);
            u.setBalance(form.getNum()+u.getBalance());
            System.out.println(u.toString());
            userService.save(u);
            return new Result(200,"充值成功！");
        }catch(AuthenticationException e){
            return new Result(400,"密码错误！");
        }

    }

    @CrossOrigin
    @PostMapping("/api/modify_pw")
    @ResponseBody
    public Result modiPassword(@RequestBody ModifyPWForm newuser,HttpServletRequest request){
        int sid= (int) request.getSession().getAttribute("sid");
        User u=userService.getBySid(sid);
        String username=u.getUsername();
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,newuser.getOld_password());
        try{
            subject.login(usernamePasswordToken);
            u.setPassword(newuser.getPassword());
            // 生成盐,默认长度 16 位
            String salt = new SecureRandomNumberGenerator().nextBytes().toString();
            // 设置 hash 算法迭代次数
            int times = 2;
            // 得到 hash 后的密码
            String encodedPassword = new SimpleHash("md5", u.getPassword(), salt, times).toString();
            // 存储用户信息，包括 salt 与 hash 后的密码
            u.setSalt(salt);
            u.setPassword(encodedPassword);
            System.out.println(u.toString());
            userService.save(u);
            return new Result(200);
        }catch(AuthenticationException e){
            return new Result(400,"旧密码错误！");
        }
    }
//
    @CrossOrigin
    @PostMapping("/api/modify_addr")
    @ResponseBody
    public Result modifyinfo(@RequestBody ModifyAddrForm newuser, HttpServletRequest request){
        int sid= (int) request.getSession().getAttribute("sid");
        User u=userService.getBySid(sid);
        u.setAddress(newuser.getAddress());

        System.out.println("new addr: "+ u.getAddress());
        userService.save(u);
        return new Result(200);
    }

    @CrossOrigin
    @PostMapping("api/comment")
    @ResponseBody
    public Result comment(@RequestBody CommentForm commentForm,HttpServletRequest request){
        int sid= (int) request.getSession().getAttribute("sid");
        //User commender=userService.getBySid(sid);
        Transaction t=transactionService.getByTransaction_id(commentForm.getTransaction_id());
        User u=userService.getBySid(t.getSeller_id());
        Comment original=commentService.getByUserIdAndGoodsId(sid,t.getGoods_id());
        int score=0;
        if(commentForm.getScore()==1)
            score=-20;
        else if(commentForm.getScore()==2)
            score=-10;
        else if (commentForm.getScore()==3)
            score=0;
        else if (commentForm.getScore()==4)
            score=10;
        else if (commentForm.getScore()==5)
            score=20;

        if(original==null) {
            u.setCredit(u.getCredit() + score);
            userService.save(u);
            Comment comment = new Comment(t.getGoods_id(), sid, score, commentForm.getComment());
            commentService.save(comment);
            return new Result(200);
        }
        else{
            u.setCredit(u.getCredit() + score-original.getScore());
            userService.save(u);
            original.setComment(commentForm.getComment());
            original.setScore(score);
            commentService.save(original);
            return new Result(201);
        }
    }
    static class record1{
        long transactionid;
        Timestamp time;
        int customer_id;
        int seller_id;
        int num;
        String name;
        String detail;
        int price;
        long total_price;
        String comment;
        public record1(long transactionid1,
                      Timestamp time1,
                      int customer_id1,
                      int seller_id1,
                      int num1,
                      String name1,
                      String detail1,
                      int price1,
                      long total_price1,
                      String comment1){
            transactionid=transactionid1;
            time=time1;
            customer_id=customer_id1;
            seller_id=seller_id1;
            num=num1;
            name=name1;
            detail=detail1;
            price=price1;
            total_price=total_price1;
            comment=comment1;
        }
        public record1(){}

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public long getTransactionid() {
            return transactionid;
        }

        public void setTransactionid(long transactionid) {
            this.transactionid = transactionid;
        }

        public Timestamp getTime() {
            return time;
        }

        public void setTime(Timestamp time) {
            this.time = time;
        }

        public int getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(int customer_id) {
            this.customer_id = customer_id;
        }

        public int getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(int seller_id) {
            this.seller_id = seller_id;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public long getTotal_price() {
            return total_price;
        }

        public void setTotal_price(long total_price) {
            this.total_price = total_price;
        }
    }
    static class record{
        long transactionid;
        Timestamp time;
        int customer_id;
        int seller_id;
        int num;
        String name;
        String detail;
        int price;
        long total_price;

        public record(long transactionid1,
                Timestamp time1,
                int customer_id1,
                int seller_id1,
                int num1,
                String name1,
                String detail1,
                int price1,
                long total_price1){
            transactionid=transactionid1;
            time=time1;
            customer_id=customer_id1;
            seller_id=seller_id1;
            num=num1;
            name=name1;
            detail=detail1;
            price=price1;
            total_price=total_price1;

        }
        public record(){}



        public long getTransactionid() {
            return transactionid;
        }

        public void setTransactionid(long transactionid) {
            this.transactionid = transactionid;
        }

        public Timestamp getTime() {
            return time;
        }

        public void setTime(Timestamp time) {
            this.time = time;
        }

        public int getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(int customer_id) {
            this.customer_id = customer_id;
        }

        public int getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(int seller_id) {
            this.seller_id = seller_id;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public long getTotal_price() {
            return total_price;
        }

        public void setTotal_price(long total_price) {
            this.total_price = total_price;
        }
    }

}