package com.example.demo.service;

import com.example.demo.dao.GoodsDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.pojo.Goods;
import com.example.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {
    @Autowired
    GoodsDAO goodsDAO;

    public long getCount(){return goodsDAO.getCount();}
    public List<Goods> findAll(){ return goodsDAO.findAll();}
    public Goods findbyGoodsId(Long goods_id){return goodsDAO.findbyGoodsId(goods_id);};

    public List<Goods> findBySellerId(int sid){return goodsDAO.findBySellerId(sid);}
    //wfl's
//    public List<Goods> findAll(){ return goodsDAO.findAll();}
    public Goods save(Goods goods) { return goodsDAO.save(goods); }
//
//    public Goods findbyGoodsId(Long goods_id){return goodsDAO.findbyGoodsId(goods_id);};
    public Goods findByGoodsName(String goods_name){
        return goodsDAO.findByGoodsName(goods_name);
    }
    public List<Goods> findAllByTag(String tag){
        return goodsDAO.findAllByTag(tag);
    }

    public List<Goods> findTop8ByOrderByGoods_idAsc(){
        return goodsDAO.findTop8ByOrderByDetail();
    }

    public List<Goods> findByGoodsInfo(String tag,String name,int lowest ,int highest,int num){
        String realName="%";
        String realTag="%";
        int realLowest;
        int realHighest;
        if(name==""){
            realName="%";
            realTag="%"+tag+"%";
        }
        else if (tag==""){
            realTag="%";
            realName="%"+name+"%";
        }
        if (highest==0){
            realHighest=Integer.MAX_VALUE;
            realLowest=0;
        }
        else {
            realHighest=highest;
            realLowest=lowest;
        }
        return goodsDAO.findByGoodsInfo(realTag,realName,realLowest,realHighest,num);

    }
}
