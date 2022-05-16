package com.example.demo.dao;

import com.example.demo.pojo.Goods;
import com.example.demo.pojo.Post;
import com.example.demo.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostDAO extends JpaRepository<Post,Integer> {
    @Query("select p from Post p where p.user_id=?1")
    List<Post> getByUserId(int user_id);
    @Query("select p from Post p where p.post_id=?1")
    Post getByPost_id(long post_id);

    @Query("select p from Post p where (p.name like ?1 or p.detail like ?1) and p.is_deleted = false ")
    List<Post> findByKeyword(String keyword);

}
