package com.lec.spring.domains.chat.repository;

import com.lec.spring.domains.chat.entity.ChatRoom;
import com.lec.spring.domains.chat.entity.ChatRoomUser;
import com.lec.spring.domains.chat.repository.dsl.QChatRoomUserRepository;
import com.lec.spring.domains.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long>, QChatRoomUserRepository {
    ChatRoomUser findByUser(User user);
    ChatRoomUser findByChatRoomAndUserId(ChatRoom chatRoom, Long userId);
}
