package com.lec.spring.domains.chat.repository.dsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QChatRoomUserRepositoryImpl implements QChatRoomUserRepository {

    private final JPAQueryFactory queryFactory;
}
