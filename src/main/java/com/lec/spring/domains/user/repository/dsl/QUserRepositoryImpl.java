package com.lec.spring.domains.user.repository.dsl;

import com.lec.spring.domains.user.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QUserRepositoryImpl implements QUserRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteByUserId(Long userId) {
        QUser qUser = QUser.user;

        queryFactory.delete(qUser)
                    .where(qUser.id.eq(userId))
                    .execute();
    }
}
