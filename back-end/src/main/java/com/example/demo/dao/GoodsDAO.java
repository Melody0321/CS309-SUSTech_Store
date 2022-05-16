package com.example.demo.dao;

import com.example.demo.pojo.Goods;
import com.example.demo.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodsDAO extends JpaRepository<Goods, Long> {
    List<Goods> findAll();
    @Query("select count(g) from Goods g")
    long getCount();
    @Query("select g from Goods g where g.goods_id=?1")
    Goods findbyGoodsId(Long goods_id);
    @Query("select g from Goods g where g.seller_id=?1")
    List<Goods> findBySellerId(int sid);

    //wfl's
//    List<Goods> findAll();
//    @Query("select g from Goods g where g.goods_id=?1")
//    Goods findbyGoodsId(Long goods_id);

    @Query("select g from Goods g where g.goods_name=?1")
    Goods findByGoodsName(String goods_name);

    @Query("select g from Goods g where g.tag like ?1 " +
            "and g.goods_name like ?2 and g.price>=?3 and g.price<=?4 and g.rest_num>=?5")
    List<Goods> findByGoodsInfo(String tag,String name,int lowest ,int highest,int num);



    List<Goods> findTop8ByOrderByDetail();

    List<Goods> findAllByTag(String tag);
}
