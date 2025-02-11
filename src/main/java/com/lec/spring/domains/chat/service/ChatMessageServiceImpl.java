package com.lec.spring.domains.chat.service;

import com.lec.spring.domains.chat.dto.ChatMessageResDTO;
import com.lec.spring.domains.chat.entity.ChatMessage;
import com.lec.spring.domains.chat.repository.ChatMessageRepository;
import com.lec.spring.domains.chat.repository.ChatRoomRepository;
import com.lec.spring.domains.chat.repository.ChatRoomUserRepository;
import com.lec.spring.global.config.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final RedisUtil redisUtil;
    private final ChatMessageRepository chatMessageRepository;

    @Override
    public ChatMessage save(Long roomId, ChatMessage chatMessage) {

        chatMessage.setRoomId(roomId);
        chatMessage.setCreatedAt(LocalDateTime.now());

        String existUsers = redisUtil.getData("room:" + roomId);

        System.out.println("existUsers: " + existUsers);

        chatMessage.setReadUsers(new ArrayList<>(Arrays.asList(existUsers.split(","))));

        return chatMessageRepository.save(chatMessage);
    }

    @Override
    public ResponseEntity<?> findAllByRoomId(Long roomId, String nickname) {
        try {

            List<ChatMessage> chatMessages = chatMessageRepository.findByRoomId(roomId);
            List<ChatMessageResDTO> resChatMessages = new ArrayList<>();
            boolean foundLastUnread = false;
            boolean firstMessage = true;

            for (ChatMessage chatMessage : chatMessages) {
                ChatMessageResDTO resChatMessage = ChatMessageResDTO.of(chatMessage);

                if (!resChatMessage.getReadUsers().contains(nickname)) {
                    if (!foundLastUnread && !firstMessage && chatMessage.getIsFirstMsg() == null) {
                        resChatMessage.setIsRead(true);
                        foundLastUnread = true;
                    }
                    resChatMessage.getReadUsers().add(nickname);
                }
                if (chatMessage.getIsFirstMsg() == null) {
                    firstMessage = false;
                }

                resChatMessages.add(resChatMessage);
            }

            chatMessageRepository.saveAll(resChatMessages);


            String readUsers = redisUtil.getData("room:" + roomId);

            if (readUsers == null) {
                redisUtil.setData("room:" + roomId, nickname);
            } else if(!readUsers.contains(nickname)) {
                redisUtil.setData("room:" + roomId, readUsers + "," + nickname);
            }

            System.out.println("readUsers: " + redisUtil.getData("room:" + roomId));

            return ResponseEntity.ok(resChatMessages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }

    }

}
