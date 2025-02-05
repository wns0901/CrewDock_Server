package com.lec.spring.domains.recruitment.controller;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/recruitments")
public class RecruitmentAttachmentController {

    // /recruitments/{recruitmentsId}/attachments	get			해당 모집글에 모든 첨부파일 불러오기
    ///recruitments/{recruitmentsId}/attachments	post	json body		첨부파일 등록
    ///recruitments/{recruitmentsId}/attachments/{attachmentId}	delete			첨부파일 삭제
}
