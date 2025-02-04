package com.lec.spring.domains.chat.repository;

import com.lec.spring.domains.chat.entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    public List<ChatMessage> findByRoomId(Long roomId);
}
