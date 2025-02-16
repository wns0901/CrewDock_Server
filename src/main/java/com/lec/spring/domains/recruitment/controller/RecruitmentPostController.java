package com.lec.spring.domains.recruitment.controller;

import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.repository.ProjectRepository;
import com.lec.spring.domains.recruitment.entity.DTO.RecruitmentPostDTO;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.service.RecruitmentPostService;
import com.lec.spring.domains.recruitment.service.RecruitmentPostServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class RecruitmentPostController {

    private final RecruitmentPostService recruitmentPostService;
    private final ProjectRepository projectRepository;

    // 모집글 전체 조회 (페이지네이션)
    @GetMapping("/recruitments")
    public ResponseEntity<Page<RecruitmentPostDTO>> recruitmentsPage(
            @RequestParam(value = "page", defaultValue = "1") int page) {
        return ResponseEntity.ok(recruitmentPostService.findAll(page));
    }

    // 모집글 필터 조회 (QueryDSL 적용)
    @GetMapping("/recruitments/filter")
    public ResponseEntity<Page<RecruitmentPostDTO>> recruitmentsFilter(
            @RequestParam(required = false) String stack,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String proceedMethod,
            @RequestParam(required = false) String region,
            @RequestParam(value = "page", defaultValue = "1") int page) {

        Pageable pageable = PageRequest.of(page - 1, 16, Sort.by(Sort.Direction.DESC, "createdAt"));
        return ResponseEntity.ok(recruitmentPostService.findByFilters(stack, position, proceedMethod, region, pageable));
    }


    // 모집 마감 임박 프로젝트 조회
    @GetMapping("/recruitments/closing")
    public ResponseEntity<Page<RecruitmentPostDTO>> closingRecruitments(
            @RequestParam(value = "page", defaultValue = "1") int page) {
        return ResponseEntity.ok(recruitmentPostService.findClosingRecruitments(page));
    }

    // 모집글 상세 조회 (DTO 반환)
    @GetMapping("/recruitments/{recruitmentsId}")
    public ResponseEntity<RecruitmentPostDTO> detailRecruitmentPost(@PathVariable("recruitmentsId") Long id) {
        try {
            RecruitmentPostDTO dto = recruitmentPostService.detailRecruitmentPost(id);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 모집글 등록 (예외 처리 추가)
    @PostMapping("/recruitments")
    public ResponseEntity<?> writeRecruitmentPost(@RequestBody RecruitmentPostDTO recruitmentPostDTO) {
        try {
            // 모집글 저장
            RecruitmentPost savedPost = recruitmentPostService.writeRecruitmentPost(recruitmentPostDTO);

            // 저장된 모집글을 DTO로 변환하여 응답
            RecruitmentPostDTO responseDTO = RecruitmentPostDTO.fromEntity(savedPost);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 유저 또는 프로젝트를 찾을 수 없습니다.");
        } catch (Exception e) {
            e.printStackTrace(); // 예외 내용 출력해볼려고
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }

    // 모집글 수정 (DTO 반환)
    @PatchMapping("/recruitments/{id}")
    public ResponseEntity<?> updateRecruitmentPost(@PathVariable Long id, @RequestBody RecruitmentPostDTO post) {
        try {
            RecruitmentPostDTO updatedPost = RecruitmentPostDTO.fromEntity(
                    recruitmentPostService.updateRecruitmentPost(id, post)
            );
            return ResponseEntity.ok(updatedPost);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 모집글이 존재하지 않습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }

    // 모집글 삭제 (상태 코드 반환)
    @DeleteMapping("/recruitments/{recruitmentsId}")
    public ResponseEntity<Void> deleteRecruitmentPost(@PathVariable("recruitmentsId") Long id) {
        try {
            recruitmentPostService.deleteRecruitmentPost(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 프로젝트 신청 (JSON Body 요청)
    @PostMapping("/projects/{projectId}/members")
    public ResponseEntity<String> applyToProject(
            @PathVariable Long projectId,
            @RequestBody Map<String, Object> request) {
        try {
            if (!request.containsKey("userId")) {
                throw new IllegalArgumentException("userId 값이 필요합니다.");
            }

            Long userId = Long.parseLong(request.get("userId").toString());
            recruitmentPostService.applyToProject(projectId, userId);

            return ResponseEntity.ok("프로젝트 신청이 완료되었습니다.");
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("userId 값이 올바르지 않습니다.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 프로젝트 또는 유저가 존재하지 않습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }

    // 캡틴 권한이 있는 프로젝트 조회
    @GetMapping("/projects/{userId}/captain")
    public ResponseEntity<List<Project>> getCaptainProjects(@PathVariable Long userId) {
        List<Project> captainProjects = projectRepository.findAllByCaptainUser(userId);
        return ResponseEntity.ok(captainProjects);
    }
}

