package com.lec.spring.domains.chat.repository.dsl;

import com.lec.spring.domains.user.entity.User;

import java.util.List;

public interface QChatRoomUserRepository {
    List<User> findUsersByRoomId(Long roomId);
}
