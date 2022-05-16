package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat_record")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Chat_record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_record_id")
    private long chat_record_id;
    @NotNull
    private int sender_id;
    @NotNull
    private int receiver_id;
    @NotNull
    private Timestamp time=new Timestamp(System.currentTimeMillis());//可能不合适
    @NotNull
    private String text;
//    @NotNull
//    private boolean from_seller;
}
