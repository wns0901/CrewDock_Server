package com.lec.spring.domains.chat.repository;

import com.lec.spring.domains.chat.entity.ChatRoom;
import com.lec.spring.domains.chat.repository.dsl.QChatRoomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, QChatRoomRepository {
}
