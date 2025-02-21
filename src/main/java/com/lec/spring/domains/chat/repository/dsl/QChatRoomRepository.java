package com.lec.spring.domains.chat.repository.dsl;

import com.lec.spring.domains.chat.dto.ChatRoomInfoResDTO;

import java.util.List;

public interface QChatRoomRepository {
    List<ChatRoomInfoResDTO> findChatRoomInfosByUserId(Long userId);
}
