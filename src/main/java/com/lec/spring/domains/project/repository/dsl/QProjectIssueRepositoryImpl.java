package com.lec.spring.domains.project.repository.dsl;

import com.lec.spring.domains.project.entity.ProjectIssue;
import com.lec.spring.domains.project.entity.ProjectIssuePriority;
import com.lec.spring.domains.project.entity.ProjectIssueStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
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
    public List<ProjectIssue> findByProjectIdSorted(Long projectId) {
        return queryFactory
                .selectFrom(projectIssue)
                .where(projectIssue.project.id.eq(projectId))
                .orderBy(
                        projectIssue.deadline.asc(),  // 1. 마감일이 가까운 순
                        getPriorityOrder(),           // 2. 우선순위 높은 순
                        getStatusOrder()              // 3. 진행중 > 시작안함 > 완료
                )
                .fetch();
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
