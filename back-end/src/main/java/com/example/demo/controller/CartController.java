package com.example.demo.controller;

import com.example.demo.Form.AddCartForm;
import com.example.demo.Form.BuyCartForm;
import com.example.demo.Form.UpdateCartForm;
import com.example.demo.pojo.Goods;
import com.example.demo.pojo.Shopping_Cart_Goods;
import com.example.demo.pojo.Transaction;
import com.example.demo.pojo.User;
import com.example.demo.result.Result;
import com.example.demo.service.CartService;
import com.example.demo.service.GoodsService;
import com.example.demo.service.TransactionService;
import com.example.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    UserService userService;
    @Autowired
    CartService cartService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    TransactionService transactionService;


    @CrossOrigin
    @GetMapping(value = "/api/get_carts")
    @ResponseBody
    public List<record> get(HttpServletRequest request){
        int id= (int) request.getSession().getAttribute("sid");
        System.out.println("get carts ing");
        List<Shopping_Cart_Goods> l1= cartService.findByCustomer_id(id);
        ArrayList<record> result=new ArrayList<>();
        for (Shopping_Cart_Goods s: l1
        ) {
            if(!s.isIs_deleted()){
                Goods goods=goodsService.findbyGoodsId(s.getGoods_id());
                record r=new record(s.getShopping_cart_goods_id(),goods.getGoods_name(),goods.getPicture(),
                        goods.getDetail(),goods.getPrice(),s.getGoods_num(),s.getTotal_price(),false,goods.getRest_num());
                result.add(r);
            }
        }
        return result;
    }
    @CrossOrigin
    @GetMapping(value = "/api/get_carts/test")
    @ResponseBody
    public List<record> get_test(HttpServletRequest request){
        System.out.println("get carts of 11111111 (test) ing");
        int id= 11111111;
        List<Shopping_Cart_Goods> l1= cartService.findByCustomer_id(id);
        ArrayList<record> result=new ArrayList<>();
        for (Shopping_Cart_Goods s: l1
             ) {
            if(!s.isIs_deleted()){
                Goods goods=goodsService.findbyGoodsId(s.getGoods_id());
                record r=new record(s.getShopping_cart_goods_id(),goods.getGoods_name(),goods.getPicture(),
                        goods.getDetail(),goods.getPrice(),s.getGoods_num(),s.getTotal_price(),false,goods.getRest_num());
                result.add(r);
            }
        }
        return result;
    }
    @CrossOrigin
    @PostMapping(value = "/api/buy_cart")
    @ResponseBody
    public Result buy(@RequestBody BuyCartForm form, HttpServletRequest request){
        int sid=(int) request.getSession().getAttribute("sid");
        User user=userService.getBySid(sid);
        Shopping_Cart_Goods cart= cartService.findByShopping_cart_goods_id(form.getId());
        Goods goods=goodsService.findbyGoodsId(cart.getGoods_id());
        int total=form.getNumber()*goods.getPrice();
        String username=user.getUsername();
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,form.getPassword());
        try{
            subject.login(usernamePasswordToken);
            if(goods.getSeller_id()==sid){
                System.out.println("不能够买自己的商品");
                return new Result(403);
            }
            if(form.getNumber()>goods.getRest_num()){
                System.out.println("余量不足");
                return new Result(402);
            }
            if(user.getBalance()<total){
                System.out.println("余额不足");
                return new Result(401);
            }


            User seller = userService.getBySid(goods.getSeller_id());
            seller.setBalance(seller.getBalance() + total);
            userService.save(seller);
            user.setBalance(user.getBalance() - total);
            userService.save(user);
            goods.setSelled_num(goods.getSelled_num() + form.getNumber());
            goods.setRest_num(goods.getRest_num() - form.getNumber());
            goodsService.save(goods);
            cart.setIs_deleted(true);
            cartService.save(cart);
            Transaction transaction = new Transaction();
            transaction.setGoods_id(goods.getGoods_id());
            transaction.setGoods_num(form.getNumber());
            transaction.setSeller_id(seller.getSID());
            transaction.setCustomer_id(sid);
            transaction.setTotal_price(total);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            transaction.setTime(timestamp);
            transactionService.save(transaction);
            return new Result(200);

        }catch(AuthenticationException e){
            return new Result(400,"旧密码错误！");
        }
    }
    @CrossOrigin
    @PostMapping(value = "/api/add_cart")
    @ResponseBody
    public Result add(@RequestBody AddCartForm form, HttpServletRequest request) {
        int sid= (int) request.getSession().getAttribute("sid");
        System.out.println(sid+"is recharging");
        User u=userService.getBySid(sid);
        Goods goods=goodsService.findbyGoodsId(form.getGoods_id());
        //if(goods.getRest_num()>= form.getNum()){
            Shopping_Cart_Goods s=new Shopping_Cart_Goods(form.getGoods_id(),form.getNum(),form.getSeller_id(),sid, form.getPrice()*form.getNum());

            //return  goods.getRest_num();
        if (u == userService.getBySid(goods.getSeller_id())) {
            System.out.println(400);//如果加的是自己的商品 添加失败
            return new Result(400);
        } else {
            //goodsService.add(requestGoods);
            System.out.println(200);
            cartService.save(s);
            return new Result(200);}

    }
    @CrossOrigin
    @PostMapping(value = "/api/update_cart")
    @ResponseBody
    public int update(@RequestBody UpdateCartForm form, HttpServletRequest request) {
        int sid= (int) request.getSession().getAttribute("sid");
        System.out.println(sid+"is recharging");
        User u=userService.getBySid(sid);
        Shopping_Cart_Goods s=cartService.findByShopping_cart_goods_id(form.getCart_id());
        Goods goods=goodsService.findbyGoodsId(s.getGoods_id());
        //if(goods.getRest_num()>= s.getGoods_num()+form.getNum()){
        s.setGoods_num(s.getGoods_num()+form.getNum());
        cartService.save(s);
        return goods.getRest_num();
        //}
        //else{
        //    return new Result(400);
        //}
    }
    @CrossOrigin
    @PostMapping(value = "/api/delete_cart")
    @ResponseBody
    public Result delete(@RequestBody UpdateCartForm form, HttpServletRequest request) {
        int sid= (int) request.getSession().getAttribute("sid");
        System.out.println(sid+"is recharging");
        User u=userService.getBySid(sid);
        Shopping_Cart_Goods s=cartService.findByShopping_cart_goods_id(form.getCart_id());
        s.setIs_deleted(true);
        cartService.save(s);
//        if (null == user) {
//            System.out.println(400);
//            return new Result(400);
//        } else {
//            goodsService.add(requestGoods);
//            System.out.println(200);
        return new Result(200);
//        }
    }
    static class record {
        long shopping_cart_goods_id;
        String goods_name;
        String picture;
        String detailed;
        int price;
        int num;
        long total_price;
        boolean is_deleted;
        int rest_num;

        public record(){}

        public record(long shopping_cart_goods_id, String goods_name, String picture, String detailed, int price, int num, long total_price, boolean is_deleted,int rest_num) {
            this.shopping_cart_goods_id = shopping_cart_goods_id;
            this.goods_name = goods_name;
            this.picture = picture;
            this.detailed = detailed;
            this.price = price;
            this.num = num;
            this.total_price = total_price;
            this.is_deleted = is_deleted;
            this.rest_num=rest_num;
        }

        public int getRest_num() {
            return rest_num;
        }

        public void setRest_num(int rest_num) {
            this.rest_num = rest_num;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public long getShopping_cart_goods_id() {
            return shopping_cart_goods_id;
        }

        public void setShopping_cart_goods_id(long shopping_cart_goods_id) {
            this.shopping_cart_goods_id = shopping_cart_goods_id;
        }

        public String getDetailed() {
            return detailed;
        }

        public void setDetailed(String detailed) {
            this.detailed = detailed;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public long getTotal_price() {
            return total_price;
        }

        public void setTotal_price(long total_price) {
            this.total_price = total_price;
        }

        public boolean isIs_deleted() {
            return is_deleted;
        }

        public void setIs_deleted(boolean is_deleted) {
            this.is_deleted = is_deleted;
        }
    }


}
