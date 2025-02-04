package com.lec.spring.domains.chat.service;

import com.lec.spring.domains.chat.entity.ChatMessage;
import com.lec.spring.domains.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    @Override
    public ChatMessage save(Long roomId, ChatMessage chatMessage) {

        chatMessage.setRoomId(roomId);
        chatMessage.setCreatedAt(LocalDateTime.now());

        chatMessageRepository.save(chatMessage);

        return chatMessage;
    }

    @Override
    public ResponseEntity<?> findAllByRoomId(Long roomId) {
        try {
            List<ChatMessage> chatMessages = chatMessageRepository.findByRoomId(roomId);
            return ResponseEntity.ok(chatMessages);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error");
        }

    }

}
