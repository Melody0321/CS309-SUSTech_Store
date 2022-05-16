package com.example.demo.dao;

import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Post;
import com.example.demo.pojo.Shopping_Cart_Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentDAO extends JpaRepository<Comment, Long> {
    @Query("select p from Comment p where p.goods_id=?1")
    List<Comment> getByGoods_id(long goods_id);
    @Query("select p from Comment p where p.user_id=?1")
    List<Comment> getByUser_id(int user_id);
    @Query("select p from Comment p where p.user_id=?1 and p.goods_id=?2")
    Comment getByUserIdAndGoodsId(int user_id,long goods_id);
}
