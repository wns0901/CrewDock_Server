package com.lec.spring.domains.recruitment.repository.dsl;

import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.recruitment.entity.ProceedMethod;
import com.lec.spring.domains.recruitment.entity.QRecruitmentPost;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.entity.Region;
import com.lec.spring.domains.stack.entity.QStack;
import com.lec.spring.domains.stack.entity.Stack;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.global.common.entity.Position;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

public class QRecruitmentPostRepositoryImpl implements QRecruitmentPostRepository {
    private final JPAQueryFactory queryFactory;

    public QRecruitmentPostRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<RecruitmentPost> filterPosts(List<Stack> stacks, List<ProceedMethod> proceedMethods, List<Region> regions) {
        // 기술스택,모집분야,진행방식,지역
        // null 나올시 전체조회

        QRecruitmentPost post = QRecruitmentPost.recruitmentPost;

        BooleanExpression condition = stacksFilter(stacks)
                .or(proceedMethodFilter(proceedMethods))
                .or(regionFilter(regions));

        return queryFactory
                .selectFrom(post)
                .where(condition)
                .fetch();
        //페이징한걸로 받아야하나 깊생하게 됨 fetchResults()
    }

    // 기술스택
    private BooleanExpression stacksFilter(List<Stack> stacks) {
        return (stacks == null || stacks.isEmpty()) ? null : QStack.stack.in(stacks);
    }
    // 모집분야
//    private BooleanExpression positionsFilter(List<Position> positions) {
//        return (positions == null || positions.isEmpty()) ? null :
//    }

    // 진행방식
    private BooleanExpression proceedMethodFilter(List<ProceedMethod> proceedMethods) {
        return (proceedMethods == null || proceedMethods.isEmpty()) ? null : QRecruitmentPost.recruitmentPost.proceedMethod.in(proceedMethods);
    }

    // 지역
    private BooleanExpression regionFilter(List<Region> regions) {
        return (regions == null || regions.isEmpty()) ? null : QRecruitmentPost.recruitmentPost.region.in(regions);
    }

//    @Override
//    public List<RecruitmentPost> myPosts(User user, Project project) {
//        QRecruitmentPost post = QRecruitmentPost.recruitmentPost;
//
//        // post userId,projectId가 있음
//        // 확인할려면
//        //내가 생성한 프로젝트들만 가져오기
//
////        user.getId() && project.getId()
//        return null;
//    }
}
