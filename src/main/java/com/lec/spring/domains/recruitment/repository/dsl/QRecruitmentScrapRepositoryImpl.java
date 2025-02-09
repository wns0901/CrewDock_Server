package com.lec.spring.domains.recruitment.repository.dsl;

import com.lec.spring.domains.recruitment.entity.RecruitmentScrap;
import com.lec.spring.domains.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.lec.spring.domains.recruitment.entity.QRecruitmentScrap.recruitmentScrap;

@RequiredArgsConstructor
public class QRecruitmentScrapRepositoryImpl implements QRecruitmentScrapRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<RecruitmentScrap> findScrapsByUserId(User user) {
        return queryFactory
                .selectFrom(recruitmentScrap)
                .where(recruitmentScrap.userId.eq(user))
                .fetch();
    }


    @Override
    public List<RecruitmentScrap> findScrapsByUserId(User user, int row) {
        return queryFactory
                .selectFrom(recruitmentScrap)
                .where(recruitmentScrap.user.eq(user))
                .limit(row)
                .fetch();
    }
}
