package com.lec.spring.domains.chat.controller;

import com.lec.spring.domains.chat.entity.ChatMessage;
import com.lec.spring.domains.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

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

}
