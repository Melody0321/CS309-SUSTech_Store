package com.example.demo.controller;

import com.example.demo.dto.PurchaseGoodsDTO;
import com.example.demo.pojo.Goods;
import com.example.demo.pojo.Transaction;
import com.example.demo.pojo.User;
import com.example.demo.result.Result;
import com.example.demo.service.GoodsService;
import com.example.demo.service.TransactionService;
import com.example.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@Controller
public class PurchaseController {

    @Autowired
    GoodsService goodsService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    UserService userService;
    @Autowired
    EmailController emailController;


    @CrossOrigin
    @PostMapping("api/purchase")
    @ResponseBody
    public Result purchase(@RequestBody PurchaseGoodsDTO purchaseGoodsDTO, HttpServletRequest request){
        System.out.println("enter the function");
        //int sid = 0;
        int sid= (int) request.getSession().getAttribute("sid");
        long goods_id = purchaseGoodsDTO.getGoods_id();

        User consumer=userService.getBySid(sid);
        Goods goods = goodsService.findbyGoodsId(goods_id);
        User seller = userService.getBySid(goods.getSeller_id());

        String password = purchaseGoodsDTO.getPassword();
        int number = purchaseGoodsDTO.getNumber();
        int totalPrize = number * goods.getPrice();
        int consumerBalance = consumer.getBalance();



        User u=userService.getBySid(sid);
        String username=u.getUsername();
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,purchaseGoodsDTO.getPassword());
        try{
            subject.login(usernamePasswordToken);
            if(goods.getSeller_id()==sid){
                System.out.println("不能够买自己的商品");
                return new Result(403);
            }
            if(number>goods.getRest_num()){
                System.out.println("余量不足");
                return new Result(402);
            }
            if(consumerBalance<totalPrize){
                System.out.println("余额不足");
                return new Result(401);
            }
        //if (consumer.getPassword().equals(password) && number<=goods.getRest_num()&&consumerBalance>=totalPrize){//密码正确且余量充足

            //add transaction
            Transaction transaction = new Transaction();
            transaction.setGoods_id(goods_id);
            transaction.setGoods_num(number);
            transaction.setSeller_id(seller.getSID());
            transaction.setCustomer_id(sid);
            transaction.setTotal_price(totalPrize);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            transaction.setTime(timestamp);
            transactionService.save(transaction);



            //update goods
            goods.setSelled_num(goods.getSelled_num()+number);
            goods.setRest_num(goods.getRest_num()-number);
            goodsService.save(goods);

            //update user  还要发送email以及更新credit！！！！
            consumer.setBalance(consumerBalance-totalPrize);
            seller.setBalance(seller.getBalance()+totalPrize);
            userService.save(consumer);
            userService.save(seller);

            System.out.println("success");

            emailController.inform1(sid,goods.getGoods_name(),totalPrize,number);
            System.out.println("already inform customer");
            emailController.inform_seller(seller.getSID(),goods.getGoods_name(),totalPrize,number);
            //emailController.inform_customer0(sid, goods.getGoods_name());

            //emailController.inform_seller0(goods.getSeller_id(),goods.getGoods_name());
            System.out.println("already inform seller");

            return new Result(200);


        }catch(AuthenticationException e){
            return new Result(400,"旧密码错误！");
        }

    }



}
