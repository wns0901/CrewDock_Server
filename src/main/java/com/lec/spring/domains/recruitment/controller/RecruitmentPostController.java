package com.lec.spring.domains.recruitment.controller;

import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.service.RecruitmentPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class RecruitmentPostController {

/*
/recruitments?[page]	        get	QS{page(defalut=1)}		                            메인페이지에 띄울 모집글 리스트
/recruitments?[….]	            get	QS{stack, proccedMethod, recruitedFiled, region}필터	메인페이지에 띄울 모집글 리스트
/recruitments/{recruitmentsId}	get			                                            모집글 상세 글(댓글, 첨부파일 포함)
/recruitments	                post	json body		                                모집글 등록
/recruitments	                patch	json body		                                모집글 수정
/recruitments/{recruitmentsId}	delete			                                        모집글 삭제
* */

    private RecruitmentPostService recruitmentPostService;

    // 메인페이지에 띄울 모집글 리스트(페이징)
    @GetMapping("/recruitments?[page]")
    public void recruitmentsPage(@RequestParam(value = "page", defaultValue = "1") Integer page){

    }

    // 메인페이지에 띄울 모집글 리스트(필터)
    @GetMapping("/recruitments?[...]")
    public void recruitments(){

    }

    // 모집글 상세 글(댓글, 첨부파일 포함)
    @GetMapping("/recruitments/{recruitmentsId}")
    public void detailRecruitmentPost(@PathVariable Long id, RecruitmentPost recruitmentPost) {
    }

    // 모집글 등록
    @PostMapping("/recruitments")
    public void writeRecruitmentPost(RecruitmentPost recruitmentPost) {
    }

    // 모집글 수정
    @PatchMapping("/recruitments")
    public void updateRecruitmentPost(RecruitmentPost recruitmentPost) {

    }

    // 모집글 삭제
    @DeleteMapping("/recruitments/{recruitmentsId}")
    public void deleteRecruitmentPost(RecruitmentPost recruitmentPost) {
    }
}
