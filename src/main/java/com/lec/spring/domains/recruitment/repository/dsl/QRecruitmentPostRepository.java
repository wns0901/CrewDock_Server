package com.lec.spring.domains.recruitment.repository.dsl;

import com.lec.spring.domains.recruitment.entity.QRecruitmentPost;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;

import java.util.List;

public interface QRecruitmentPostRepository {
    // 전체 프로젝트 검색 조건을 여기다가 쓰겠어
    // 기술스택/포지션/진행방식/포지션=모집분야
    // 리스트 형식의 뭐를 하고는

    //List<Member> result = queryFactory
    //        .select(member)
    //        .from(member)
    //        .where(usernameEq(username))
    //        .fetch();
    //출처: https://ittrue.tistory.com/292 [IT is True:티스토리]

    /**
     * 필터링 수행
     * @param condition
     * @param category
     * @param keyword
     * @return
     */

    //https://chaeyami.tistory.com/258

    // 정말 정직하게 튜토리얼 보고합니다.
    // https://www.baeldung.com/querydsl-with-jpa-tutorial
    QRecruitmentPost recruitmentPost = QRecruitmentPost.recruitmentPost;

    // 조건문이 정말 많다~ // 기술스택/포지션/진행방식/지역
    // recruitmentPost 에다가
    //
    private List<RecruitmentPost> searchRecruitmentPost(RecruitmentPost recruitmentPost){
        return null;
    };

    // 마감임박 페이징...
}
