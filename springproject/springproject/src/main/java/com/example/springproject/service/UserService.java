package com.example.springproject.service;

import com.example.springproject.domain.UserInfo;

public interface UserService {
    public void savewithFk(UserInfo userInfo, long id);
}
