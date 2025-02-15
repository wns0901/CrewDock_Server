package com.lec.spring.domains.recruitment.repository.dsl;

import com.lec.spring.domains.project.entity.QProject;
import com.lec.spring.domains.project.entity.QProjectStacks;
import com.lec.spring.domains.project.service.ProjectService;
import com.lec.spring.domains.project.service.ProjectStacksService;
import com.lec.spring.domains.project.service.ProjectStacksServiceImpl;
import com.lec.spring.domains.recruitment.entity.DTO.RecruitmentPostDTO;
import com.lec.spring.domains.recruitment.entity.QRecruitmentPost;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.entity.Region;
import com.lec.spring.domains.recruitment.repository.RecruitmentPostRepository;
import com.lec.spring.domains.user.entity.QUser;
import com.lec.spring.domains.user.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.lec.spring.domains.user.entity.QUser.user;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Repository("qrecruitmentPostRepository")
public class QRecruitmentPostRepositoryImpl implements QRecruitmentPostRepository {
    private final JPAQueryFactory queryFactory;
    private final ProjectStacksServiceImpl projectStacksService;

    public QRecruitmentPostRepositoryImpl(JPAQueryFactory queryFactory, ProjectStacksServiceImpl projectStacksService) {
        this.queryFactory = queryFactory;
        this.projectStacksService = projectStacksService;
    }

    // 모집글 상세 조회
    @Override
    public Optional<RecruitmentPost> findByIdWithUserAndProject(Long id) {
        QRecruitmentPost post = QRecruitmentPost.recruitmentPost;
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
        QRecruitmentPost post = QRecruitmentPost.recruitmentPost;

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

        // List<RecruitmentPost> -> List<RecruitmentPostDTO> 변환
        List<RecruitmentPostDTO> dtoList = results.stream()
                .map(RecruitmentPostDTO::fromEntity)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, total);
    }


    // 필터 메서드들
    // stack 필터
    private BooleanExpression stackFilter(String stack) {
        if (stack == null || stack.isEmpty()) {
            return null;
        }

        List<Long> projectIds = projectStacksService.findProjectsByStack(stack); // ✅ 특정 스택을 가진 프로젝트 ID 조회

        if (projectIds.isEmpty()) {
            return Expressions.FALSE; // ✅ 해당 스택을 가진 프로젝트가 없으면 false 반환 (모집글 없음)
        }

        return QRecruitmentPost.recruitmentPost.project.id.in(projectIds); // ✅ 프로젝트 ID 리스트를 기반으로 필터링
    }


    private BooleanExpression positionFilter(String position) {
        return (position == null || position.isEmpty()) ? null :
                QRecruitmentPost.recruitmentPost.recruitedField.containsIgnoreCase(position);
    }

    private BooleanExpression proceedMethodFilter(String proceedMethod) {
        return (proceedMethod == null || proceedMethod.isEmpty()) ? null :
                QRecruitmentPost.recruitmentPost.proceedMethod.stringValue().equalsIgnoreCase(proceedMethod);
    }

    private BooleanExpression regionFilter(String region) {
        return (region == null || region.isEmpty()) ? null :
                QRecruitmentPost.recruitmentPost.region.stringValue().equalsIgnoreCase(region);
    }
}
