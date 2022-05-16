package com.example.demo.controller;

import com.example.demo.Form.PostForm;
import com.example.demo.Form.UpdateCartForm;
import com.example.demo.Form.UpdatePostForm;
import com.example.demo.pojo.Post;
import com.example.demo.pojo.Post;
import com.example.demo.pojo.User;
import com.example.demo.result.Result;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PostController {
    @Autowired
    PostService postService;
    @Autowired
    UserService userService;


    @GetMapping("/post/keyword")
    public List<Post> findByKeyword(@RequestParam String keyword)throws Exception{
        System.out.println("keyword function enter");

        List<Post> result = postService.findByKeyword(keyword);
        for (Post p:result
             ) {
            System.out.println(p.getName()+p.getDetail());
        }

        return result;
    }




    @CrossOrigin
    @PostMapping("api/post")
    @ResponseBody
    public Result postnew(@RequestBody PostForm postform, HttpServletRequest request){
        int sid= (int) request.getSession().getAttribute("sid");
        User u=userService.getBySid(sid);
        if (postform.getName()!=""){
            Post post=new Post(sid,postform.getName(),postform.getDetail());
            postService.add(post);
            return new Result(200);
        }
        else
            return new Result(400);
    }

    //get不从这里，从personal的posted_record里
    @CrossOrigin
    @GetMapping("api/getpost")
    @ResponseBody
    public List<Post> getPost(HttpServletRequest request){
        int sid= (int) request.getSession().getAttribute("sid");
        User u=userService.getBySid(sid);
        List<Post> all= postService.getByUserId(sid);
        ArrayList<Post> re=new ArrayList<>();
        for (Post p:all
             ) {
            System.out.println(p.toString());
            if(!p.isIs_deleted())
                re.add(p);
        }
        return re;
        //return null;
    }

    @CrossOrigin
    @PostMapping(value = "/api/update_post")
    @ResponseBody
    public Result update(@RequestBody UpdatePostForm form, HttpServletRequest request) {
        int sid= (int) request.getSession().getAttribute("sid");
        System.out.println(sid+"is recharging");
        User u=userService.getBySid(sid);
        Post s=postService.getByPost_id(form.getPost_id());
        s.setName(form.getName());
        s.setDetail(form.getDetail());
        postService.save(s);

        return new Result(200);
//        }
    }
    @CrossOrigin
    @PostMapping(value = "/api/delete_post")
    @ResponseBody
    public Result delete(@RequestBody UpdatePostForm form, HttpServletRequest request) {
        int sid= (int) request.getSession().getAttribute("sid");
        System.out.println(sid+"is recharging");
        User u=userService.getBySid(sid);
        Post s=postService.getByPost_id(form.getPost_id());
        s.setIs_deleted(true);
        postService.save(s);
//        if (null == user) {
//            System.out.println(400);
//            return new Result(400);
//        } else {
//            goodsService.add(requestGoods);
//            System.out.println(200);
        return new Result(200);
//        }
    }
}
