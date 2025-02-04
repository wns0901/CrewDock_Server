package com.lec.spring.domains.chat.service;

import com.lec.spring.domains.chat.entity.ChatMessage;
import org.springframework.http.ResponseEntity;

public interface ChatMessageService {
    ChatMessage save(Long roomId,ChatMessage chatMessage);

    ResponseEntity<?> findAllByRoomId(Long roomId, String nickname);
}
