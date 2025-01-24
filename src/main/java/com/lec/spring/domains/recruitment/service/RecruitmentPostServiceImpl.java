package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class RecruitmentPostServiceImpl implements RecruitmentPostService {
    @Override
    public List<RecruitmentPost> RecruitmentPostMainPage(Integer page, Model model) {
        // 어떻게 페이징+필터링 하지

        return List.of();
    }

    // 음ㅁㅁ 고민
    @Override
    public RecruitmentPost detailRecruitmentPost(Long id) {
        RecruitmentPost recruitmentPost = new RecruitmentPost();
        id = recruitmentPost.getId();
        return null;
    }

    // 모집글 페이징
    @Override
    public List<RecruitmentPost> RecruitmentPostlist(Integer page, Model model) {
        // 스위치문 사용하기(근데 필터링이 기술 스택, 포지션, 진행 방식, 지역이네)
        return List.of();
    }

    @Override
    public RecruitmentPost writeRecruitmentPost(RecruitmentPost recruitmentPost) {
        // 입력 받아야할 값들(제목, 소개, 내용 작성)
        /*
        * 기본 정보를 작성하거나 Select 할 수 있으며,
        * 프로젝트 생성 시에 작성된 기본 정보인 진행기간, 기술스택, 프로젝트명이 그대로 나온다
        * 프로젝트 시작시 작성되어 있던 프로젝트 명을 보여줌. (수정은 프로젝트 설정에서 가능)
         * 모집글 제목 및 소개 및 내용 작성 영역
         */

        return null;
    }

    @Override
    public RecruitmentPost updateRecruitmentPost(RecruitmentPost recruitmentPost, MultipartFile file) {
        return null;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }
}
