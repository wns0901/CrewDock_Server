package com.lec.spring.domains.chat.controller;

import com.lec.spring.domains.chat.dto.StartChatRoomDto;
import com.lec.spring.domains.chat.entity.ChatMessage;
import com.lec.spring.domains.chat.entity.ChatRoom;
import com.lec.spring.domains.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;

    @MessageMapping("/room/{roomId}")
    @SendTo("/sub/room/{roomId}")
    public ChatMessage send(@DestinationVariable Long roomId, ChatMessage chatMessage) {
        chatMessage.setRoomId(roomId);

        return chatMessageService.save(roomId, chatMessage);
    }

    @MessageMapping("/user/{userId}")
    @SendTo("/sub/user/{userId}")
    public StartChatRoomDto startChatRoom(@DestinationVariable Long userId, ChatRoom chatRoom) {
        System.out.println("userId: " + userId + ", roomId: " + chatRoom);
        return StartChatRoomDto.builder()
                .userId(userId)
                .roomId(chatRoom.getId())
                .build();
    }

    @GetMapping("/chat-messages")
    @ResponseBody
    public ResponseEntity<?> findAllByRoomId(@RequestParam Long roomId, @RequestParam String nickname) {
        return chatMessageService.findAllByRoomId(roomId, nickname);
    }

}
