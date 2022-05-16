package com.example.demo.dto;


import lombok.Data;

@Data
public class ChatDTO {
    private int sender_id;
    private int receiver_id;
    private String text;
}
