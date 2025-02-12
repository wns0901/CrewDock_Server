package com.lec.spring.domains.recruitment.repository.dsl;

import com.lec.spring.domains.project.entity.QProject;
import com.lec.spring.domains.recruitment.dto.RecruitmentPostCommentsDTO;
import com.lec.spring.domains.recruitment.entity.*;
import com.lec.spring.domains.recruitment.entity.DTO.RecruitmentPostDTO;
import com.lec.spring.domains.recruitment.repository.RecruitmentPostRepository;
import com.lec.spring.domains.user.entity.QUser;
import com.lec.spring.domains.user.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.lec.spring.domains.post.entity.QPost.post;
import static com.lec.spring.domains.recruitment.entity.QRecruitmentComment.recruitmentComment;
import static com.lec.spring.domains.recruitment.entity.QRecruitmentPost.recruitmentPost;
import static com.lec.spring.domains.user.entity.QUser.user;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Repository("qrecruitmentPostRepository")
@RequiredArgsConstructor
public class QRecruitmentPostRepositoryImpl implements QRecruitmentPostRepository {
    private final JPAQueryFactory queryFactory;
    private final QRecruitmentPost post = recruitmentPost;  // ✅ Q클래스 사용
    private final QRecruitmentComment comment = recruitmentComment;

    // 모집글 상세 조회
    @Override
    public Optional<RecruitmentPost> findByIdWithUserAndProject(Long id) {
        QRecruitmentPost post = recruitmentPost;
        QUser user = QUser.user;
        QProject project = QProject.project;

        RecruitmentPost recruitmentPost = queryFactory
                .selectFrom(post)
                .leftJoin(post.user, user).fetchJoin()
                .leftJoin(post.project, project).fetchJoin()
                .where(post.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(recruitmentPost);
    }

    // 모집글 필터 조회
    @Override
    public Page<RecruitmentPostDTO> findByFilters(String stack, String position, String proceedMethod, String region, Pageable pageable) {
        QRecruitmentPost post = recruitmentPost;

        List<RecruitmentPost> results = queryFactory
                .selectFrom(post)
                .where(
                        stackFilter(stack),
                        positionFilter(position),
                        proceedMethodFilter(proceedMethod),
                        regionFilter(region)
                )
                .orderBy(post.createdAt.desc())
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

        // 리스트를 DTO로 변환
        List<RecruitmentPostDTO> dtoList = results.stream()
                .map(RecruitmentPostDTO::fromEntity)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, total);
    }


    // 모집 마감 임박 프로젝트 조회 (마감 3일 전)
    @Override
    public Page<RecruitmentPostDTO> findClosingRecruitments(LocalDate closingDate, Pageable pageable) {
        QRecruitmentPost post = recruitmentPost;

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

        // List<RecruitmentPost> -> List<RecruitmentPostDTO> 변환
        List<RecruitmentPostDTO> dtoList = results.stream()
                .map(RecruitmentPostDTO::fromEntity)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, total);
    }

    @Override
    public List<RecruitmentPostCommentsDTO> findAllByUserId2(Long userId) {
        return queryFactory
                .selectFrom(post)
                .where(post.user.id.eq(userId))
                .fetch()
                .stream()
                .map(p -> {
                    List<RecruitmentComment> comments = queryFactory
                            .selectFrom(comment)
                            .where(comment.post.isNotNull().and(comment.post.id.eq(p.getId())))  // ✅ 수정된 부분
                            .fetch();
                    return RecruitmentPostCommentsDTO.fromEntity(p, comments);
                })
                .collect(Collectors.toList());
    }


    // ✅ 특정 유저가 작성한 모집글 + 댓글 (row 제한)
    @Override
    public List<RecruitmentPostCommentsDTO> findByUserIdWithLimit(Long userId, Pageable pageable) {
        return queryFactory
                .selectFrom(recruitmentPost)
                .where(recruitmentPost.user.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(p -> {
                    List<RecruitmentComment> comments = queryFactory
                            .selectFrom(comment)
                            .where(comment.post.isNotNull().and(comment.post.id.eq(p.getId())))  // ✅ 수정된 부분
                            .fetch();
                    return RecruitmentPostCommentsDTO.fromEntity(p, comments);
                })
                .collect(Collectors.toList());
    }



    // 필터 메서드들
    private BooleanExpression stackFilter(String stack) {
        return (stack == null || stack.isEmpty()) ? null :
                recruitmentPost.recruitedField.containsIgnoreCase(stack);
    }

    private BooleanExpression positionFilter(String position) {
        return (position == null || position.isEmpty()) ? null :
                recruitmentPost.recruitedField.containsIgnoreCase(position);
    }

    private BooleanExpression proceedMethodFilter(String proceedMethod) {
        return (proceedMethod == null || proceedMethod.isEmpty()) ? null :
                recruitmentPost.proceedMethod.stringValue().equalsIgnoreCase(proceedMethod);
    }

    private BooleanExpression regionFilter(String region) {
        return (region == null || region.isEmpty()) ? null :
                recruitmentPost.region.stringValue().equalsIgnoreCase(region);
    }
}
