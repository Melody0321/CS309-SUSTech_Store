package com.example.demo.dto;

import lombok.Data;

import java.sql.Timestamp;


@Data
public class ChatDetailDTO {


//    private long chat_record_id;
    private int sender_id;
    private int receiver_id;
    private String sender_name;
    private String receiver_name;
    private String time;
    private String text;
}
