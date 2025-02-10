package com.lec.spring.domains.chat.service;

import com.lec.spring.domains.chat.entity.ChatRoom;
import com.lec.spring.domains.chat.entity.ChatRoomUser;
import com.lec.spring.domains.chat.repository.ChatRoomRepository;
import com.lec.spring.domains.chat.repository.ChatRoomUserRepository;
import com.lec.spring.global.config.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomUserServiceImpl implements ChatRoomUserService {

    private final ChatRoomUserRepository chatRoomUserRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public ResponseEntity<?> deleteChatRoomUser(Long roomId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Long userId = ((PrincipalDetails) authentication.getPrincipal()).getUser().getId();

            ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new IllegalArgumentException("Chat room not found"));
            ChatRoomUser chatRoomUser = chatRoomUserRepository.findByChatRoomAndUserId(chatRoom, userId);

            chatRoomUserRepository.delete(chatRoomUser);

            if(chatRoom.getUserCnt() == 1) {
                chatRoomRepository.delete(chatRoom);
            } else {
                chatRoom.setUserCnt(chatRoom.getUserCnt() - 1);
                chatRoomRepository.save(chatRoom);
            }

            return ResponseEntity.ok().body("Chat room user deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete chat room user");
        }

    }

    @Override
    public ResponseEntity<?> changeRoomName(Long roomId,String roomName) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Long userId = ((PrincipalDetails) authentication.getPrincipal()).getUser().getId();

            ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new IllegalArgumentException("Chat room not found"));

            ChatRoomUser chatRoomUser = chatRoomUserRepository.findByChatRoomAndUserId(chatRoom, userId);
            chatRoomUser.setRoomName(roomName);
            chatRoomUserRepository.save(chatRoomUser);
            return ResponseEntity.ok().body("Room name changed");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to change room name");
        }
    }
}
