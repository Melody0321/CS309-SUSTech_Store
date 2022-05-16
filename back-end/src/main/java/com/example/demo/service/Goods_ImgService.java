package com.example.demo.service;

import com.example.demo.dao.Goods_ImgDAO;
import com.example.demo.pojo.Goods_img;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Goods_ImgService {
    @Autowired
    Goods_ImgDAO goods_imgDAO;

    public List<Goods_img> getByGoods_id(long goods_id){
        return goods_imgDAO.getByGoods_id(goods_id);
    }
    public Goods_img getByGoods_img_id(long goods_img_id){
        return goods_imgDAO.getByGoods_img_id(goods_img_id);
    }
    public void save(Goods_img g){
        goods_imgDAO.save(g);
    }

}
