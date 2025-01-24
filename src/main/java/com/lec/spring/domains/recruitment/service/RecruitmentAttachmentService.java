package com.lec.spring.domains.recruitment.service;

import com.lec.spring.domains.recruitment.entity.RecruitmentAttachment;

public interface RecruitmentAttachmentService {
    // 특정 모집글에 첨부파일 조회하기
    RecruitmentAttachment file(Long id);

//    // 특정 모집글에 첨부파일 추가
//    // 클라이언트>백엔드>db저장
//    RecruitmentAttachment save(RecruitmentAttachment recruitmentAttachment);
//
//    // 삭제하기
//    int delete(Long id);
}
