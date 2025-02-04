package com.lec.spring.domains.chat.service;

import com.lec.spring.domains.chat.entity.ChatMessage;
import com.lec.spring.domains.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

}
