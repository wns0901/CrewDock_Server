package com.lec.spring.domains.user.repository.dsl;

import com.lec.spring.domains.stack.entity.QStack;
import com.lec.spring.domains.user.dto.UserResponseDTO;
import com.lec.spring.domains.user.entity.QUser;
import com.lec.spring.domains.user.entity.QUserStacks;
import com.lec.spring.domains.user.entity.User;
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

    @Override
    public UserResponseDTO getUserWithStacks(Long userId) {
        QUser user = QUser.user;
        QUserStacks userStacks = QUserStacks.userStacks;
        QStack stack = QStack.stack;


        User userInfo = queryFactory
                .selectFrom(user)
                .leftJoin(user.userStacks, userStacks).fetchJoin()
                .leftJoin(userStacks.stack, stack).fetchJoin()
                .where(user.id.eq(userId))
                .fetchOne();

        if (userInfo == null) {
            return null;
        }

        return UserResponseDTO.fromEntity(userInfo);
    }
}
