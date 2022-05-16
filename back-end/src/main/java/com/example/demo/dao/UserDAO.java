package com.example.demo.dao;

import com.example.demo.pojo.User;
import com.example.demo.result.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
//        Query user’s basic info: GET ∼ /api/mainpage/id
//        Query posted record: GET ∼ /api/posted_record/id
//        Query sales record: GET ∼ /api/sales_record/id
//        Query purchase record: GET ∼ /api/purchase_record/id
//        Recharge: POST ∼ /api/rechargeid
//        Modify info: POST ∼ /api/modi f y_in f oid
public interface UserDAO extends JpaRepository<User,Integer> {

    User getBySid(int sid);
    User getByUsername(String username);
    User findByUsername(String username);

    User getByUsernameAndPassword(String username,String password);

    List<User> findAll();



}