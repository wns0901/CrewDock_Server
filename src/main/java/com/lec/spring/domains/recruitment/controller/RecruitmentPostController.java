package com.lec.spring.domains.recruitment.controller;

import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.service.ProjectService;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.service.RecruitmentPostService;
import com.lec.spring.domains.recruitment.service.RecruitmentPostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class RecruitmentPostController {

    private final RecruitmentPostService recruitmentPostService;
    private final ProjectService projectService;
    private final RecruitmentPostServiceImpl recruitmentPostServiceImpl;

    // 모집글 페이지
    @GetMapping("/recruitments")
    public ResponseEntity<Page<RecruitmentPost>> recruitmentsPage(@RequestParam(value = "page", defaultValue = "1") int page) {
        return ResponseEntity.ok(recruitmentPostService.findAll(page));
    }

    // 모집글 필터링 (페이징 지원)
    @GetMapping("/recruitments/filter")
    public ResponseEntity<Page<RecruitmentPost>> recruitmentsFilter(
            @RequestParam(required = false) String stack,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String proceedMethod,
            @RequestParam(required = false) String region,
            @RequestParam(value = "page", defaultValue = "1") int page) {

        return ResponseEntity.ok(recruitmentPostService.findByFilters(stack, position, proceedMethod, region, page));
    }

    // 모집 마감 임박 프로젝트 (마감 3일 전, 최대 20개, 잔여 모집 인원 수 적은 순 정렬)
    @GetMapping("/recruitments/closing")
    public ResponseEntity<Page<RecruitmentPost>> closingRecruitments(@RequestParam(value = "page", defaultValue = "1") int page) {
        return ResponseEntity.ok(recruitmentPostService.findClosingRecruitments(page));
    }

    // 모집글 상세 조회
    @GetMapping("/recruitments/{recruitmentsId}")
    public RecruitmentPost detailRecruitmentPost(@PathVariable("recruitmentsId") Long id) {
        //TODO: 값 정리

        // {
        //    "userId": {
        //        "id": 1
        //    },
        //    "project": {
        //        "id": 2
        //    },
        //    "title": "백엔드 개발자 모집",
        //    "content": "함께할 백엔드 개발자를 찾습니다. Spring Boot와 JPA를 사용한 경험이 있는 분을 선호합니다.",
        //    "deadline": "2025-02-28",
        //    "region": "SEOUL",
        //    "proceedMethod": "ONLINE",
        //    "recruitedNumber": 3,
        //    "recruitedField": "백엔드",
        //    "createAt": "2025-02-07T12:00:00"
        //}

        // 추구미
        return recruitmentPostService.detailRecruitmentPost(id);
    }

    // 모집글 등록
    @PostMapping("/recruitments")
    public void writeRecruitmentPost(@RequestBody RecruitmentPost recruitmentPost) {
        recruitmentPostService.writeRecruitmentPost(recruitmentPost);
    }

    // 모집글 수정
    @PatchMapping("/recruitments/{recruitmentsId}")
    public void updateRecruitmentPost(@PathVariable("recruitmentsId") Long id, @RequestBody RecruitmentPost recruitmentPost) {
        recruitmentPostService.updateRecruitmentPost(id, recruitmentPost);
    }

    // 모집글 삭제
    @DeleteMapping("/recruitments/{recruitmentsId}")
    public void deleteRecruitmentPost(@PathVariable("recruitmentsId") Long id) {
        recruitmentPostService.deleteRecruitmentPost(id);
    }

    // 프로젝트 신청 (json body)
    @PostMapping("/projects/{projectId}/members")
    public ResponseEntity<String> applyToProject(
            @PathVariable Long projectId,
            @RequestBody Map<String, Object> request) {

        Long userId = Long.parseLong(request.get("userId").toString());

        recruitmentPostServiceImpl.applyToProject(projectId, userId); // 프로젝트 ID 먼저 받도록 변경

        return ResponseEntity.ok("프로젝트 신청이 완료되었습니다.");
    }

    // 캡틴의 모든 프로젝트 목록 조회
    @GetMapping("/projects/{userId}")
    public ResponseEntity<List<Project>> getCaptainProjects(@RequestParam Long userId) {
        return ResponseEntity.ok(projectService.getCaptainProjects(userId));
    }
//

}

