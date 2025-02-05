package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecruitmentPostService {

    // Paging
    Page<RecruitmentPost> findAll(Pageable pageable);

    // 필터옵션

    // Read(상세 글 보여주기)
    RecruitmentPost detailRecruitmentPost(Long id, Project period);

    // 내 프로젝트 가져오기
    // 리스트형식으로 가져옴
    List<RecruitmentPost> myRecruitmentPost(Long id, User userId);

    // 프로젝트 선택하기

    // 선택한 프로젝트 가져오기

    // 모집글 적기
    RecruitmentPost writeRecruitmentPost (RecruitmentPost recruitmentPost);

    // 모집글 수정
    RecruitmentPost updateRecruitmentPost (Long id,RecruitmentPost recruitmentPost);

    // 모집글 삭제
    int deleteRecruitmentPost (Long id);

}
