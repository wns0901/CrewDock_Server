package com.lec.spring.domains.project.repository.dsl;

import com.lec.spring.domains.project.dto.ProjectIssueDTO;
import com.lec.spring.domains.project.entity.ProjectIssue;
import com.lec.spring.domains.project.entity.ProjectIssuePriority;
import com.lec.spring.domains.project.entity.ProjectIssueStatus;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.lec.spring.domains.project.entity.QProjectIssue.projectIssue;

@Repository
public class QProjectIssueRepositoryImpl implements QProjectIssueRepository {

    private final JPAQueryFactory queryFactory;

    public QProjectIssueRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<ProjectIssueDTO> findByProjectIdSorted(Long projectId) {
        return queryFactory
                .select(Projections.constructor(ProjectIssueDTO.class,
                        projectIssue.id, // issueId
                        projectIssue.issueName, // issueName
                        projectIssue.status, // status
                        projectIssue.priority, // priority
                        projectIssue.deadline, // deadline
                        projectIssue.startline, // startline
                        projectIssue.createdAt.as("createAt"),
                        projectIssue.writer.id.as("writerId"), // writerId
                        projectIssue.writer.nickname.as("writerName"), // writerName
                        projectIssue.manager.id.as("managerId"), // managerId
                        projectIssue.manager.nickname.as("managerName"), // managerName
                        projectIssue.project.id.as("projectId") // projectId
                ))
                .from(projectIssue)
                .where(projectIssue.project.id.eq(projectId))
                .orderBy(
                        getStatusOrder(),
                        projectIssue.deadline.asc(),
                        getPriorityOrder()
                )
                .fetch();
    }

    @Override
    public List<ProjectIssue> findAllById(List<Long> issueIds) {
        return queryFactory.selectFrom(projectIssue)
                .where(idIn(issueIds))
                .fetch();
    }

    private BooleanExpression idIn(List<Long> issueIds) {
        if (issueIds == null || issueIds.isEmpty()) {
            return null; // 빈 리스트에 대해 처리를 하지 않음
        }
        return projectIssue.id.in(issueIds);
    }


    // 우선순위 정렬 (HIGH > MIDDLE > LOW)
    private com.querydsl.core.types.OrderSpecifier<Integer> getPriorityOrder() {
        return new com.querydsl.core.types.OrderSpecifier<>(
                com.querydsl.core.types.Order.DESC,
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
                com.querydsl.core.types.Order.ASC,
                new com.querydsl.core.types.dsl.CaseBuilder()
                        .when(projectIssue.status.eq(ProjectIssueStatus.INPROGRESS)).then(1)
                        .when(projectIssue.status.eq(ProjectIssueStatus.YET)).then(2)
                        .when(projectIssue.status.eq(ProjectIssueStatus.COMPLETE)).then(3)
                        .otherwise(4)
        );
    }
}
