package com.lec.spring.domains.chat.service;

import org.springframework.http.ResponseEntity;

public interface ChatRoomUserService {
    ResponseEntity<?> deleteChatRoomUser(Long roomId);
    ResponseEntity<?> changeRoomName(Long roomId, String roomName);
}
