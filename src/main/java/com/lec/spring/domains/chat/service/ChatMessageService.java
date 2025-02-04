package com.lec.spring.domains.chat.service;

import com.lec.spring.domains.chat.dto.CreateChatRoomReqDTO;
import com.lec.spring.domains.chat.entity.ChatMessage;
import com.lec.spring.domains.chat.entity.ChatRoom;
import com.lec.spring.domains.chat.entity.ChatRoomUser;
import org.springframework.http.ResponseEntity;

public interface ChatMessageService {
    ChatMessage save(Long roomId,ChatMessage chatMessage);

    ChatRoomUser inviteUser(Long roomId, Long userId);
}
