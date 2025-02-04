package com.lec.spring.domains.chat.service;

import com.lec.spring.domains.chat.entity.ChatMessage;

public interface ChatMessageService {
    ChatMessage save(Long roomId,ChatMessage chatMessage);
}
