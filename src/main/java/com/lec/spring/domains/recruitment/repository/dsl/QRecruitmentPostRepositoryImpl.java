package com.lec.spring.domains.recruitment.repository.dsl;

import com.lec.spring.domains.recruitment.entity.QRecruitmentPost;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.entity.Region;
import com.lec.spring.domains.recruitment.repository.RecruitmentPostRepository;
import com.lec.spring.domains.user.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.lec.spring.domains.user.entity.QUser.user;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Repository("qrecruitmentPostRepository") //정확한 Bean 이름 지정
public class QRecruitmentPostRepositoryImpl implements QRecruitmentPostRepository {
    private final JPAQueryFactory queryFactory;

    public QRecruitmentPostRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    // 모집글을 조회하면 글작성자와 프로젝트를 조회(상세조회때 사용)
    @Override
    public Optional<RecruitmentPost> findByIdWithUserAndProject(Long id) {
        QRecruitmentPost post = QRecruitmentPost.recruitmentPost;

        RecruitmentPost result = queryFactory
                .selectFrom(post)
                .where(post.id.eq(id))
                .leftJoin(post.user).fetchJoin() // 유저 정보 가져오기
                .leftJoin(post.project).fetchJoin() // 프로젝트 정보 가져오기
                .fetchFirst();

        return Optional.ofNullable(result);
    }


    // 필터
    @Override
    public Page<RecruitmentPost> findByFilters(String stack, String position, String proceedMethod, String region, Pageable pageable) {
        QRecruitmentPost post = QRecruitmentPost.recruitmentPost;

        List<RecruitmentPost> results = queryFactory
                .selectFrom(post)
                .where(
                        stackFilter(stack),
                        positionFilter(position),
                        proceedMethodFilter(proceedMethod),
                        regionFilter(region)
                )
                .orderBy(post.createdAt.desc()) // 최신순 정렬
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(post)
                .where(
                        stackFilter(stack),
                        positionFilter(position),
                        proceedMethodFilter(proceedMethod),
                        regionFilter(region)
                )
                .fetchCount();

        return new PageImpl<>(results, pageable, total);
    }

    private BooleanExpression stackFilter(String stack) {
        return (stack == null || stack.isEmpty()) ? null :
                QRecruitmentPost.recruitmentPost.recruitedField.containsIgnoreCase(stack);
    }

    private BooleanExpression positionFilter(String position) {
        return (position == null || position.isEmpty()) ? null :
                QRecruitmentPost.recruitmentPost.recruitedField.containsIgnoreCase(position);
    }

    private BooleanExpression proceedMethodFilter(String proceedMethod) {
        return (proceedMethod == null || proceedMethod.isEmpty()) ? null :
                QRecruitmentPost.recruitmentPost.proceedMethod.stringValue().eq(proceedMethod);
    }

    private BooleanExpression regionFilter(String region) {
        return (region == null || region.isEmpty()) ? null :
                QRecruitmentPost.recruitmentPost.region.stringValue().eq(region);
    }


    // 마감 3일전
    @Override
    public Page<RecruitmentPost> findClosingRecruitments(LocalDate closingDate, Pageable pageable) {
        QRecruitmentPost post = QRecruitmentPost.recruitmentPost;

        List<RecruitmentPost> results = queryFactory
                .selectFrom(post)
                .where(post.deadline.loe(closingDate))  // 마감일이 closingDate(3일 이내)보다 작거나 같은 모집글 필터링
                .orderBy(post.deadline.asc(), post.recruitedNumber.asc()) // 마감일 오름차순, 잔여 모집 인원 적은 순 정렬
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(post)
                .where(post.deadline.loe(closingDate))
                .fetchCount();

        return new PageImpl<>(results, pageable, total);
    }

}

