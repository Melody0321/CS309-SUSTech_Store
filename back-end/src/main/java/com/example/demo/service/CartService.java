package com.example.demo.service;

import com.example.demo.dao.CartDAO;
import com.example.demo.pojo.Shopping_Cart_Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    CartDAO shoppingCartGoodsDAO;
    public List<Shopping_Cart_Goods> findByCustomer_id(int customer_id){
        return shoppingCartGoodsDAO.findByCustomer_id(customer_id);
    }
    public Shopping_Cart_Goods findByShopping_cart_goods_id(long shopping_cart_goods_id){
        return shoppingCartGoodsDAO.findByShopping_cart_goods_id(shopping_cart_goods_id);
    }
    public void save(Shopping_Cart_Goods shopping_cart_goods){
        shoppingCartGoodsDAO.save(shopping_cart_goods);}
}
