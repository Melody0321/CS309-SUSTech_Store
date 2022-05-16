package com.example.demo.service;

import com.example.demo.dao.CommentDAO;
import com.example.demo.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentDAO commentDAO;
    public List<Comment> getByGoods_id(long goods_id){
        System.out.println("comment service called!");
        return commentDAO.getByGoods_id(goods_id);
    }
    public void save(Comment c){commentDAO.save(c);}
    public List<Comment> getByUser_id(int user_id){
        return commentDAO.getByUser_id(user_id);
    }
    public Comment getByUserIdAndGoodsId(int user_id,long goods_id){
        return commentDAO.getByUserIdAndGoodsId(user_id,goods_id);
    }
}
