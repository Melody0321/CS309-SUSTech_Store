package com.example.demo.service;


import com.example.demo.dao.ChatDAO;
import com.example.demo.pojo.Chat_record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    @Autowired
    ChatDAO chatDAO;

    public List<Chat_record> getBySender_idAndReceiver_id(int sender_id,int receiver_id){
        return chatDAO.getBySender_idAndReceiver_idOrderByTime(sender_id,receiver_id);
    }

    public List<Chat_record> getById(int self_id){
        return chatDAO.getByIdOrderByTime(self_id);
    }

    public void save(Chat_record chat_record) {
        chatDAO.save(chat_record);
    }
}
