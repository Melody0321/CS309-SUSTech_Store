package com.example.demo.service;

import com.example.demo.dao.GoodsDAO;
import com.example.demo.dao.TransactionDAO;
import com.example.demo.pojo.Goods;
import com.example.demo.pojo.Transaction;
import com.example.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Service
public class TransactionService {
    @Autowired
    TransactionDAO transactionDAO;
    @Autowired
    GoodsDAO goodsDAO;
    public List<Transaction> getBySellerId(int seller_id){return transactionDAO.getBySellerId(seller_id);}
    public List<Transaction> getByCustomerId(int customer_id){return transactionDAO.getByCustomerId(customer_id);}
    public List<Transaction> getByGoodsId(long goods_id){return transactionDAO.getByGoodsId(goods_id);};
    public Transaction getByTransaction_id(long transaction_id){
        return transactionDAO.getByTransaction_id(transaction_id);
    }
//    public List<Long> getBySellerId(int seller_id){
//        return transactionDAO.getBySellerId(seller_id);
//    }
//    public List<Goods> getGoodsBySellerId(int seller_id){
//        List<Long> l=getBySellerId(seller_id);
//
//        List<Goods> result=null;
//        for (Long i:l
//             ) {
//            System.out.println(i);
//            result.add(goodsDAO.findbyGoodsId(i));
//        }
//        return result;
//    };
//    public List<Long> getByCustomerId(int customer_id){
//        return transactionDAO.getByCustomerId(customer_id);}
//    public List<Goods> getGoodsByCustomerId(int customer_id){
//        List<Long> l=getByCustomerId(customer_id);
//        List<Goods> result=null;
//        for (Long i:l
//        ) {
//            result.add(goodsDAO.findbyGoodsId(i));
//        }
//        return result;
//    };
    public void save(Transaction transaction){transactionDAO.save(transaction);}
}
