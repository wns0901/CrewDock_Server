package com.lec.spring.domains.chat.repository;

import com.lec.spring.domains.chat.entity.ChatMessage;
import com.lec.spring.domains.chat.entity.ChatRoom;
import com.lec.spring.domains.chat.entity.ChatRoomUser;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.repository.UserRepository;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChatMessageRepositoryTest {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatRoomUserRepository chatRoomUserRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void test() {
        System.out.println("test");
        System.out.println(LocalDateTime.now());
        ChatMessage chatMessagesg = ChatMessage.builder()
                .roomId(1L)
                .msg("test")
                .sender("test")
                .readUsers(List.of("other1","other2"))
                .createdAt(LocalDateTime.now())
                .build();

        chatMessageRepository.save(chatMessagesg);
        System.out.println("save 완료");
        List<ChatMessage> chatMessages = chatMessageRepository.findByRoomId(1L);
        chatMessages.forEach(System.out::println);
    }

    @Test
    public void createChatRoom() {
        ChatRoom chatRoom = ChatRoom.builder()
                .userCnt(2)
                .build();

        chatRoomRepository.save(chatRoom);

        User user1 = userRepository.findById(1L).orElse(null);
        User user2 = userRepository.findById(2L).orElse(null);

        ChatRoomUser chatRoomUser = ChatRoomUser.builder()
                .chatRoom(chatRoom)
                .user(user1)
                .roomName("room1")
                .build();

        ChatRoomUser chatRoomUser2 = ChatRoomUser.builder()
                .chatRoom(chatRoom)
                .user(user2)
                .roomName("room1")
                .build();

        chatRoomUserRepository.saveAll(List.of(chatRoomUser, chatRoomUser2));
    }

}