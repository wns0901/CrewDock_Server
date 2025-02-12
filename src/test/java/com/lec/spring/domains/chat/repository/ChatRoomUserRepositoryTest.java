//package com.lec.spring.domains.chat.repository;
//
//import com.lec.spring.domains.chat.entity.ChatRoom;
//import com.lec.spring.domains.chat.entity.ChatRoomUser;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class ChatRoomUserRepositoryTest {
//
//    @Autowired
//    private ChatRoomUserRepository chatRoomUserRepository;
//
//    @Autowired
//    private ChatRoomRepository chatRoomRepository;
//
//    @Test
//    void findByUser() {
//        ChatRoom chatRoom = chatRoomRepository.findById(1L).orElse(null);
//
//        ChatRoomUser chatRoomUser = chatRoomUserRepository.findByChatRoomAndUserId(chatRoom, 1L);
//
//        System.out.println(chatRoomUser);
//    }
//
//}