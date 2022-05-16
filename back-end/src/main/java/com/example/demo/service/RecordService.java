package com.example.demo.service;

import com.example.demo.dao.GoodsDAO;
import com.example.demo.dao.TransactionDAO;
import com.example.demo.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    GoodsDAO goodsDAO;
    @Autowired
    TransactionDAO transactionDAO;


}
