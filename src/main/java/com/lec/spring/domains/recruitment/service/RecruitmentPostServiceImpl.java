package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class RecruitmentPostServiceImpl implements RecruitmentPostService {
    @Override
    public List<RecruitmentPost> RecruitmentPostlist(Integer page, Model model) {
        // 어떻게 페이징+카테고리를 하지
        return List.of();
    }

    @Override
    public RecruitmentPost detailRecruitmentPost(Long id) {
        return null;
    }

    @Override
    public RecruitmentPost writeRecruitmentPost(RecruitmentPost recruitmentPost, MultipartFile file) {
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
