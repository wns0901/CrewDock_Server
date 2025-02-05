package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.repository.RecruitmentPostRepository;
import com.lec.spring.domains.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public class RecruitmentPostServiceImpl implements RecruitmentPostService {

    private final RecruitmentPostRepository recruitmentPostRepository;

    public RecruitmentPostServiceImpl(RecruitmentPostRepository recruitmentPostRepository) {
        this.recruitmentPostRepository = recruitmentPostRepository;
    }

    // Paging
    @Override
    public Page<RecruitmentPost> findAll(Pageable pageable) {
        return recruitmentPostRepository.findAll(pageable);
    }


    // 필터옵션



    // Read(상세 글 보여주기)
    @Override
    public RecruitmentPost detailRecruitmentPost(Long id, Project period) {
        return recruitmentPostRepository.findById(id)
                .orElseThrow();
    }

    @Override
    public List<RecruitmentPost> myRecruitmentPost(Long id, User user) {
        return List.of();
    }

    // Create
    @Override
    public RecruitmentPost writeRecruitmentPost(RecruitmentPost recruitmentPost) {
        // 입력 들어가야할 값
        //모집분야/진행기간/모집마감일/지역/진행방식/모집인원/기술스택/프로젝트명
        //제목/프로젝트 소개 및 내용

        RecruitmentPost Post = new RecruitmentPost();


        recruitmentPostRepository.save(recruitmentPost);

        //검증

        return Post;
    }

    // Update
    @Override
    public RecruitmentPost updateRecruitmentPost(Long id,RecruitmentPost recruitmentPost) {
        //원래 있는거 확인하고 id값 가져오고 없으면 에러 띄우기
        RecruitmentPost Post = null;
        recruitmentPostRepository.save(recruitmentPost);
        //검증

        return null;
    }

    // Delete
    @Override
    public int deleteRecruitmentPost(Long id) {
        if (!recruitmentPostRepository.existsById(id)) {
            throw new EntityNotFoundException("해당 모집글을 찾을 수 없습니다. id: " + id);
        }
        recruitmentPostRepository.deleteById(id);
        return 1;
    }
}
