package com.lec.spring.domains.recruitment.repository.dsl;

import com.lec.spring.domains.recruitment.entity.QRecruitmentComment;
import com.lec.spring.domains.recruitment.entity.RecruitmentComment;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("qrecruitmentCommentRepository") // 정확한 Bean 이름 추가!
@RequiredArgsConstructor
public class QRecruitmentCommentRepositoryImpl implements QRecruitmentCommentRepository {

    private final JPAQueryFactory queryFactory;

    QRecruitmentComment comment = QRecruitmentComment.recruitmentComment;
    QRecruitmentComment parentComment = new QRecruitmentComment("parentComment");

    // ✅ 모집글에 속한 모든 댓글 조회 (부모-자식 댓글 계층 구조 유지)
    public List<RecruitmentComment> commentListByRecruitmentPost(RecruitmentPost recruitmentPost) {
        System.out.println("모집글 ID: " + recruitmentPost.getId());

        List<RecruitmentComment> comments = queryFactory
                .selectFrom(comment)
                .leftJoin(comment.comment, parentComment).fetchJoin() // ✅ 부모 댓글 정보 함께 조회
                .leftJoin(comment.user).fetchJoin() // ✅ 유저 정보도 함께 조회
                .where(comment.post.id.eq(recruitmentPost.getId()))
                .orderBy(comment.comment.id.asc().nullsFirst(), comment.id.asc()) // ✅ 부모 댓글이 먼저 오도록 정렬
                .fetch();

        System.out.println("조회된 댓글 개수: " + comments.size());

        // ✅ 디버깅 코드 추가 (댓글 목록 확인)
        for (RecruitmentComment c : comments) {
            System.out.println("🔍 댓글 ID: " + c.getId() + ", 부모 댓글 ID: " +
                    (c.getComment() != null ? c.getComment().getId() : "없음"));
        }

        return comments;
    }

    // ✅ 특정 부모 댓글의 대댓글 조회
    @Override
    public List<RecruitmentComment> findRepliesByParentComment(RecruitmentComment parentComment) {
        System.out.println("🔍 부모 댓글 ID: " + parentComment.getId() + "에 대한 대댓글 조회");

        List<RecruitmentComment> replies = queryFactory
                .select(comment)
                .from(comment)
                .leftJoin(comment.user).fetchJoin() // ✅ 유저 정보도 함께 가져오기
                .where(comment.comment.eq(parentComment)) // ✅ 특정 부모 댓글의 대댓글만 조회
                .orderBy(comment.createdAt.asc()) // ✅ 작성일 기준 정렬
                .fetch();

        System.out.println("대댓글 개수: " + replies.size());

        for (RecruitmentComment r : replies) {
            System.out.println("📝 대댓글 ID: " + r.getId() + ", 내용: " + r.getContent());
        }

        return replies;
    }
}
