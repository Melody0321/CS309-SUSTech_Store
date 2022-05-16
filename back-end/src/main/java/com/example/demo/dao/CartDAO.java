package com.example.demo.dao;

import com.example.demo.pojo.Shopping_Cart_Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartDAO extends JpaRepository<Shopping_Cart_Goods, Long> {
    @Query("select g from Shopping_Cart_Goods g where g.shopping_cart_goods_id=?1")
    Shopping_Cart_Goods findByShopping_cart_goods_id(long shopping_cart_goods_id);
    @Query("select g from Shopping_Cart_Goods g where g.customer_id=?1")
    List<Shopping_Cart_Goods> findByCustomer_id(int customer_id);
}
