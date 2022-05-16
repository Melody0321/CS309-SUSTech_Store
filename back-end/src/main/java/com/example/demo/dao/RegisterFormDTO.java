package com.example.demo.dao;

import com.example.demo.pojo.RegisterForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterFormDTO extends JpaRepository<RegisterForm,Integer> {

    RegisterForm findByUsername(String username);

    RegisterForm getByUsernameAndPassword(String username,String password);
}