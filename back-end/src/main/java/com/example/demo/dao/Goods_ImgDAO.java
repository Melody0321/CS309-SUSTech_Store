package com.example.demo.dao;

import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Goods_img;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Goods_ImgDAO extends JpaRepository<Goods_img, Long> {
    @Query("select p from Goods_img p where p.goods_id=?1")
    List<Goods_img> getByGoods_id(long goods_id);

    @Query("select p from Goods_img p where p.goods_img_id=?1")
    Goods_img getByGoods_img_id(long goods_img_id);

}
