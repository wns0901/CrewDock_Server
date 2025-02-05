package com.lec.spring.domains.recruitment.repository.dsl;

import com.lec.spring.domains.recruitment.entity.QRecruitmentPost;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QRecruitmentPostRepositoryImpl implements QRecruitmentPostRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<RecruitmentPost> findByIdWithUserAndProject(Long id) {
        QRecruitmentPost post = QRecruitmentPost.recruitmentPost;

        RecruitmentPost result = queryFactory
                .selectFrom(post)
                .leftJoin(post.user).fetchJoin()
                .leftJoin(post.project).fetchJoin()
                .where(post.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<RecruitmentPost> findByFilters(List<String> stacks, List<String> recruitedFields, List<String> regions) {
        QRecruitmentPost post = QRecruitmentPost.recruitmentPost;

        return queryFactory
                .selectFrom(post)
                .where(
                        stackFilter(stacks),
                        recruitedFieldFilter(recruitedFields),
                        regionFilter(regions)
                )
                .fetch();
    }

    private BooleanExpression stackFilter(List<String> stacks) {
        return (stacks == null || stacks.isEmpty()) ? null : QRecruitmentPost.recruitmentPost.recruitedField.containsIgnoreCase(stacks.get(0));
    }

    private BooleanExpression recruitedFieldFilter(List<String> recruitedFields) {
        if (recruitedFields == null || recruitedFields.isEmpty()) {
            return null;
        }
        BooleanExpression condition = null;
        for (String field : recruitedFields) {
            BooleanExpression fieldCondition = QRecruitmentPost.recruitmentPost.recruitedField.containsIgnoreCase(field);
            condition = (condition == null) ? fieldCondition : condition.or(fieldCondition);
        }
        return condition;
    }

    private BooleanExpression regionFilter(List<String> regions) {
        return (regions == null || regions.isEmpty()) ? null : QRecruitmentPost.recruitmentPost.region.stringValue().in(regions);
    }
}
