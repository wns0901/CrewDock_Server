package com.lec.spring.domains.chat.controller;

import com.lec.spring.domains.chat.dto.CreateChatRoomReqDTO;
import com.lec.spring.domains.chat.entity.ChatRoom;
import com.lec.spring.domains.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat-room")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/chat-room")
    @ResponseBody
    public ResponseEntity<?> StartPersonalChat(@RequestBody CreateChatRoomReqDTO createChatRoomReqDTO) {
        try {
            ChatRoom chatRoom = chatRoomService.createChatRoom(createChatRoomReqDTO);

            if (chatRoom == null) return ResponseEntity.badRequest().body("Chat room can't create");

            return ResponseEntity.ok().body(chatRoom);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
