package com.example.demo.dao;

import com.example.demo.pojo.Chat_record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatDAO extends JpaRepository<Chat_record,Long> {
    @Query("select ch from Chat_record ch where (ch.sender_id=?1 and ch.receiver_id=?2 )or (ch.sender_id=?2 and ch.receiver_id=?1 ) order by ch.time")
    List<Chat_record> getBySender_idAndReceiver_idOrderByTime(int sender_id,int receiver_id);



    @Query("select ch from Chat_record ch where ch.sender_id=?1 or ch.receiver_id=?1  order by ch.time")
    List<Chat_record> getByIdOrderByTime(int self_id);
}
