package com.example.demo.service;

import com.example.demo.dao.PostDAO;
import com.example.demo.pojo.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    PostDAO postDAO;
    public Post getByPost_id(long post_id){return postDAO.getByPost_id(post_id);}
    public List<Post> getByUserId(int user_id){return postDAO.getByUserId(user_id);}
    public void add(Post post) {
        postDAO.save(post);
    }

    public void save(Post post){postDAO.save(post);}

    public List<Post> findByKeyword(String keyword){

        String dbKeyword = "%"+keyword+"%";
        return postDAO.findByKeyword(dbKeyword);
    }
}
