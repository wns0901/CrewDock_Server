package com.lec.spring.domains.recruitment.repository;

import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.entity.RecruitmentScrap;
import com.lec.spring.domains.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitmentScrapRepository extends JpaRepository<RecruitmentScrap, Long> {

    //유저랑 모집글이 있는지 확인
    boolean existsByUserAndRecruitmentPost(User user, RecruitmentPost recruitmentPost);

    // 스크랩 지우기
    void deleteByUserAndRecruitmentPost(User user, RecruitmentPost recruitmentPost);

    // 스크랩 출력하기
    List<RecruitmentScrap> findByUser(User user);
}

