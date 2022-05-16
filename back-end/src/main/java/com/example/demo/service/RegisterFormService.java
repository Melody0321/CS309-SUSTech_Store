package com.example.demo.service;

import com.example.demo.pojo.RegisterForm;
import com.example.demo.dao.RegisterFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RegisterFormService {
    @Autowired
    RegisterFormDTO dto;

    public boolean isExist(String username) {
        RegisterForm RegisterForm = getByName(username);
        return null!=RegisterForm;
    }

    public RegisterForm getByName(String username) {
        return dto.findByUsername(username);
    }

    public RegisterForm get(String username, String password){
        return dto.getByUsernameAndPassword(username, password);
    }

    public void add(RegisterForm user) {
        dto.save(user);
    }
}
