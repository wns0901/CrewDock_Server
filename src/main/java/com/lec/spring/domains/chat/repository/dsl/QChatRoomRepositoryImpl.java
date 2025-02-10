package com.lec.spring.domains.chat.repository.dsl;

import com.lec.spring.domains.chat.dto.ChatRoomInfoResDTO;
import com.lec.spring.domains.chat.entity.ChatRoom;
import com.lec.spring.domains.chat.entity.QChatRoom;
import com.lec.spring.domains.chat.entity.QChatRoomUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class QChatRoomRepositoryImpl implements QChatRoomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ChatRoomInfoResDTO> findChatRoomInfosByUserId(Long userId) {
        QChatRoom chatRoom = QChatRoom.chatRoom;
        QChatRoomUser chatRoomUser = QChatRoomUser.chatRoomUser;

        return queryFactory
            .select(Projections.constructor(ChatRoomInfoResDTO.class, chatRoom, chatRoomUser.roomName.as("roomName")))
            .from(chatRoom)
            .join(chatRoomUser)
            .on(chatRoomUser.chatRoom.eq(chatRoom))
            .where(chatRoomUser.user.id.eq(userId))
            .fetch();
    }
}
