package com.example.demo.controller;


import com.example.demo.dto.ChatDTO;
import com.example.demo.dto.ChatDetailDTO;
import com.example.demo.pojo.Chat_record;
import com.example.demo.pojo.Goods;
import com.example.demo.pojo.User;
import com.example.demo.result.Result;
import com.example.demo.service.ChatService;
import com.example.demo.service.GoodsService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

@RestController
public class ChatController {
    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;


    @GetMapping("api/getselfid")
    public ResponseEntity<?> findSelfId(HttpServletRequest httpServletRequest){

        int self_id = (int)httpServletRequest.getSession().getAttribute("sid");
//            int self_id = 2;
        int [] id_list =new int[1];
        id_list[0]= self_id;
        return ResponseEntity.ok(id_list);
    }


    @GetMapping("api/judgeid")
    public Result judgeSelfAndOtherid(@RequestParam int other_id,HttpServletRequest httpServletRequest){

        int self_id = (int)httpServletRequest.getSession().getAttribute("sid");
//            int self_id = 2;
        if(self_id ==other_id){
            return new Result(404);
        }
        else return new Result(200);
    }


    @GetMapping("api/getid")
    public ResponseEntity<?> findSelfAndOtherid(@RequestParam long goods_id,HttpServletRequest httpServletRequest){
            Goods goods = goodsService.findbyGoodsId(goods_id);
            int other_id = goods.getSeller_id();
            int self_id = (int)httpServletRequest.getSession().getAttribute("sid");
//            int self_id = 2;
            int [] id_list =new int[2];
            id_list[0]= self_id;
            id_list[1] = other_id;
        return ResponseEntity.ok(id_list);
    }
    @GetMapping("api/allmessages")
    public ResponseEntity<?> findAllChatMessages(
           HttpServletRequest httpServletRequest) {


                int self_id =(int) httpServletRequest.getSession().getAttribute("sid");
        // 测试用
//        int self_id = 1;
        int sender_id;
        int receiver_id;
        List<Chat_record> chat_recordList = chatService.getById(self_id);
        Set<Integer> other_id_list = new HashSet<>();
        for(int i=0; i<chat_recordList.size();i++){
            sender_id = chat_recordList.get(i).getSender_id();
            receiver_id = chat_recordList.get(i).getReceiver_id();
            if (sender_id!=self_id)
                other_id_list.add(sender_id);
            if(receiver_id!=self_id)
            other_id_list.add(receiver_id);
        }
        return ResponseEntity
                .ok(other_id_list);
    }


    @GetMapping("api/messages")
    public ResponseEntity<?> findChatMessages(
                                              @RequestParam int other_id,HttpServletRequest httpServletRequest) {

//
                int self_id =(int) httpServletRequest.getSession().getAttribute("sid");
        // 测试用
//        int self_id = 1;
        List<Chat_record> chat_record_list = chatService.getBySender_idAndReceiver_id(self_id,other_id);
        List<ChatDetailDTO> details = new LinkedList<>();
        for (Chat_record chatRecord : chat_record_list) {
            ChatDetailDTO chatDetailDTO = new ChatDetailDTO();
            chatDetailDTO.setSender_id(chatRecord.getSender_id());
            chatDetailDTO.setReceiver_id(chatRecord.getReceiver_id());
            chatDetailDTO.setText(chatRecord.getText());
            String time = String.valueOf(chatRecord.getTime());
            time = time.substring(0, time.length() - 4);
            System.out.println(time);
            chatDetailDTO.setTime(time);

            User sender = userService.getBySid(chatRecord.getSender_id());
            if (sender == null)
                chatDetailDTO.setSender_name("sender not exist");
            else
                chatDetailDTO.setSender_name(sender.getUsername());
            User receiver = userService.getBySid(chatRecord.getReceiver_id());
            if (receiver == null)
                chatDetailDTO.setReceiver_name("receiver not exist");
            else
                chatDetailDTO.setReceiver_name(receiver.getUsername());

            details.add(chatDetailDTO);
        }
        return ResponseEntity
                .ok(details);
    }

    @CrossOrigin
    @PostMapping("api/savemessage")
    @ResponseBody
    public Result saveMessages(@RequestBody ChatDTO chatDTO){
        System.out.println("start save messages");
        System.out.println("Receiver id: "+chatDTO.getReceiver_id());
        System.out.println("sender id: "+chatDTO.getSender_id());
//        int sender_id =(int) httpServletRequest.getSession().getAttribute("sid");
        // 测试用
//        int sender_id = 3;

        Chat_record chat_record = new Chat_record();
        chat_record.setSender_id(chatDTO.getSender_id());
        chat_record.setReceiver_id(chatDTO.getReceiver_id());
        chat_record.setText(chatDTO.getText());
        chat_record.setTime(new Timestamp(System.currentTimeMillis()));
        chatService.save(chat_record);
        System.out.println("success");
        return new Result(200);
    }


    @MessageMapping("/chat")
    public void processMessage(@Payload ChatDTO chatDTO){
        System.out.println("start send messages web socket");
//        int sender_id =(int) httpServletRequest.getSession().getAttribute("sid");
        // 测试用
//        int sender_id = 3;

        int sender_id = chatDTO.getSender_id();
        int receiver_id = chatDTO.getReceiver_id();
        ChatDetailDTO chatDetailDTO = new ChatDetailDTO();
        chatDetailDTO.setSender_id(sender_id);
        chatDetailDTO.setReceiver_id(receiver_id);
        chatDetailDTO.setText(chatDTO.getText());
        String time = String.valueOf(new Timestamp(System.currentTimeMillis()));
        time = time.substring(0,time.length()-4);
        System.out.println(time);
        chatDetailDTO.setTime(time);

        User sender = userService.getBySid(sender_id);
        if (sender==null)
            chatDetailDTO.setSender_name("sender not exist");
        else
            chatDetailDTO.setSender_name(sender.getUsername());
        User receiver = userService.getBySid(receiver_id);
        if (receiver==null)
            chatDetailDTO.setReceiver_name("receiver not exist");
        else
            chatDetailDTO.setReceiver_name(receiver.getUsername());

        messagingTemplate.convertAndSendToUser(String.valueOf(chatDTO.getReceiver_id()),
                "/queue/messages",chatDetailDTO);
    }

}
