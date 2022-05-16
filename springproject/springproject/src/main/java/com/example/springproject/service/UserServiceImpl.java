package com.example.springproject.service;

import com.example.springproject.api.UserRepository;
import com.example.springproject.domain.City;
import com.example.springproject.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public void savewithFk(UserInfo userInfo, long id) {
        City city = entityManager.getReference(City.class, id);
        userInfo.setCity(city);
        userRepository.save(userInfo);
    }
}
