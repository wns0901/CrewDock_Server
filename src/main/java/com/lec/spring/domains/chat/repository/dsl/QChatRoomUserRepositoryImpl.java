package com.lec.spring.domains.chat.repository.dsl;

import com.lec.spring.domains.chat.entity.QChatRoomUser;
import com.lec.spring.domains.user.entity.User;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class QChatRoomUserRepositoryImpl implements QChatRoomUserRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<User> findUsersByRoomId(Long roomId) {
        QChatRoomUser chatRoomUser = QChatRoomUser.chatRoomUser;

        return queryFactory.select(Projections.bean(User.class, chatRoomUser.user))
                .from(chatRoomUser)
                .where(chatRoomUser.chatRoom.id.eq(roomId))
                .fetch();
    }
}
