package com.lec.spring.domains.chat.controller;

import com.lec.spring.domains.chat.dto.ChangeRoomNameReqDTO;
import com.lec.spring.domains.chat.dto.CreateChatRoomReqDTO;
import com.lec.spring.domains.chat.entity.ChatRoom;
import com.lec.spring.domains.chat.service.ChatRoomService;
import com.lec.spring.domains.chat.service.ChatRoomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat-rooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatRoomUserService chatRoomUserService;

    @PostMapping("")
    public ResponseEntity<?> StartPersonalChat(@RequestBody CreateChatRoomReqDTO createChatRoomReqDTO) {
        try {
            ChatRoom chatRoom = chatRoomService.createChatRoom(createChatRoomReqDTO);

            if (chatRoom == null) return ResponseEntity.badRequest().body("Chat room can't create");

            return ResponseEntity.ok().body(chatRoom);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getChatRoomList(@PathVariable Long userId) {
        return chatRoomService.getChatRoomList(userId);
    }

    @PatchMapping("/{roomId}/{nickname}")
    public ResponseEntity<?> leaveChatRoom(@PathVariable Long roomId, @PathVariable String nickname) {
        return chatRoomService.leaveChatRoom(roomId, nickname);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<?> deleteChatRoom(@PathVariable Long roomId) {
        return chatRoomUserService.deleteChatRoomUser(roomId);
    }

    @PatchMapping("/{roomId}")
    public ResponseEntity<?> changeRoomName(@PathVariable Long roomId, @RequestBody ChangeRoomNameReqDTO changeRoomNameReqDTO) {
        return chatRoomUserService.changeRoomName(roomId, changeRoomNameReqDTO.getRoomName());
    }
}
