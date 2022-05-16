package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "chatting_record")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Chatting_Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatting_record_id")
    private long chatting_record_id;
    @NotNull
    private long seller_id;
    @NotNull
    private long customer_id;
    @NotNull
    private String time;
    @NotNull
    private String text;
}
