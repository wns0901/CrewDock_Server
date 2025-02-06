package com.lec.spring.domains.project.repository.dsl;

import com.lec.spring.domains.project.dto.ProjectIssueDTO;
import com.lec.spring.domains.project.entity.ProjectIssuePriority;
import com.lec.spring.domains.project.entity.ProjectIssueStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.lec.spring.domains.project.entity.QProjectIssue.projectIssue;
import static com.lec.spring.domains.user.entity.QUser.user;

@Repository
public class QProjectIssueRepositoryImpl implements QProjectIssueRepository {

    private final JPAQueryFactory queryFactory;

    public QProjectIssueRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<ProjectIssueDTO> findByProjectIdSorted(Long projectId) {
        return queryFactory
                .select(
                        projectIssue.id,
                        projectIssue.issueName,
                        projectIssue.status,
                        projectIssue.priority,
                        projectIssue.deadline,
                        projectIssue.startline,
                        projectIssue.createAt,
                        projectIssue.writer.id,
                        projectIssue.manager.id,
                        projectIssue.manager.name,
                        projectIssue.project.id
                )
                .from(projectIssue)
                .where(projectIssue.project.id.eq(projectId))
                .orderBy(
                        projectIssue.deadline.asc(),
                        getPriorityOrder(),
                        getStatusOrder()
                )
                .fetch()
                .stream()
                .map(result -> new ProjectIssueDTO(
                        result.get(0, Long.class),
                        result.get(1, String.class),
                        result.get(2, ProjectIssueStatus.class),
                        result.get(3, ProjectIssuePriority.class),
                        result.get(4, LocalDate.class),
                        result.get(5, LocalDate.class),
                        result.get(6, LocalDateTime.class),
                        result.get(7, Long.class),
                        result.get(8, Long.class),
                        result.get(9, String.class),
                        result.get(10, Long.class)
                ))
                .toList();
    }


    // 우선순위 정렬 (HIGH > MIDDLE > LOW)
    private com.querydsl.core.types.OrderSpecifier<Integer> getPriorityOrder() {
        return new com.querydsl.core.types.OrderSpecifier<>(
                com.querydsl.core.types.Order.DESC,  // 내림차순으로 변경
                new com.querydsl.core.types.dsl.CaseBuilder()
                        .when(projectIssue.priority.eq(ProjectIssuePriority.HIGH)).then(3)
                        .when(projectIssue.priority.eq(ProjectIssuePriority.MIDDLE)).then(2)
                        .when(projectIssue.priority.eq(ProjectIssuePriority.LOW)).then(1)
                        .otherwise(0)
        );
    }

    // 상태 정렬 (INPROGRESS > YET > COMPLETE)
    private com.querydsl.core.types.OrderSpecifier<Integer> getStatusOrder() {
        return new com.querydsl.core.types.OrderSpecifier<>(
                com.querydsl.core.types.Order.DESC,  // 내림차순으로 변경
                new com.querydsl.core.types.dsl.CaseBuilder()
                        .when(projectIssue.status.eq(ProjectIssueStatus.INPROGRESS)).then(3)
                        .when(projectIssue.status.eq(ProjectIssueStatus.YET)).then(2)
                        .when(projectIssue.status.eq(ProjectIssueStatus.COMPLETE)).then(1)
                        .otherwise(0)
        );
    }
}
