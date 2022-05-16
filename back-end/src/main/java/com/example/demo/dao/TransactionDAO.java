package com.example.demo.dao;

import com.example.demo.pojo.Goods;
import com.example.demo.pojo.Transaction;
import com.example.demo.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionDAO extends JpaRepository<Transaction,Long> {
    @Query("select t from Transaction t where t.goods_id=?1")
    List<Transaction> getByGoodsId(long goods_id);
//    @Query("select t.goods_id from Transaction t where t.seller_id=?1")
//    List<Long> getBySellerId(int seller_id);
//    @Query("select t.goods_id from Transaction t where t.customer_id=?1")
//    List<Long> getByCustomerId(int customer_id);
    @Query("select t from Transaction t where t.seller_id=?1")
    List<Transaction> getBySellerId(int seller_id);
    @Query("select t from Transaction t where t.customer_id=?1")
    List<Transaction> getByCustomerId(int customer_id);
    @Query("select t from Transaction t where t.transaction_id=?1")
    Transaction getByTransaction_id(long transaction_id);

}
