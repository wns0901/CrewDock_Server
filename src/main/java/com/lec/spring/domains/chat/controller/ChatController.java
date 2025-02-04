package com.lec.spring.domains.chat.controller;

import com.lec.spring.domains.chat.entity.ChatMessage;
import com.lec.spring.domains.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("/chat-message/{roomId}")
    @ResponseBody
    public ResponseEntity<?> findAllByRoomId(@PathVariable Long roomId, @RequestParam String nickname) {
        return chatMessageService.findAllByRoomId(roomId, nickname);
    }
}
