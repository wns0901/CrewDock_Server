package com.lec.spring.domains.chat.service;

import com.lec.spring.domains.chat.dto.CreateChatRoomReqDTO;
import com.lec.spring.domains.chat.entity.ChatMessage;
import com.lec.spring.domains.chat.entity.ChatRoom;
import com.lec.spring.domains.chat.entity.ChatRoomUser;
import com.lec.spring.domains.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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


    @Override
    public ChatRoomUser inviteUser(Long roomId, Long userId) {
        return null;
    }

}
