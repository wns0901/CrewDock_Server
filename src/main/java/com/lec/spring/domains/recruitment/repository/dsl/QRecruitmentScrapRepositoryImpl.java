package com.lec.spring.domains.recruitment.repository.dsl;

import com.lec.spring.domains.recruitment.dto.ScrappedPostDTO;
import com.lec.spring.domains.recruitment.entity.QRecruitmentComment;
import com.lec.spring.domains.recruitment.entity.QRecruitmentPost;
import com.lec.spring.domains.recruitment.entity.QRecruitmentScrap;
import com.lec.spring.domains.recruitment.entity.RecruitmentScrap;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QRecruitmentScrapRepositoryImpl implements QRecruitmentScrapRepository {


    private final JPAQueryFactory queryFactory;

    @Override
    public List<ScrappedPostDTO> findScrappedPostsWithCommentCount(Long userId, int row) {
        QRecruitmentScrap recruitmentScrap = QRecruitmentScrap.recruitmentScrap;
        QRecruitmentPost recruitmentPost = QRecruitmentPost.recruitmentPost;
        QRecruitmentComment recruitmentComment = QRecruitmentComment.recruitmentComment;

        var query = queryFactory
                .select(Projections.constructor(
                        ScrappedPostDTO.class,
                        recruitmentScrap.id,                   // recruitmentScrapId
                        recruitmentPost.id,                    // recruitmentPostId
                        recruitmentPost.title,                 // title
                        recruitmentScrap.createdAt,            // createdAt
                        recruitmentComment.count()             // commentCount
                ))
                .from(recruitmentScrap)
                .leftJoin(recruitmentScrap.recruitment, recruitmentPost)
                .leftJoin(recruitmentComment).on(recruitmentComment.post.eq(recruitmentScrap.recruitment))
                .where(recruitmentScrap.user.id.eq(userId))
                .groupBy(recruitmentScrap.id)
                .orderBy(recruitmentScrap.createdAt.desc());

        if (row > 0) {
            query.limit(row);
        }

        return query.fetch();
    }
}