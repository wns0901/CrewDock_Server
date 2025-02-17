package com.lec.spring.domains.recruitment.controller;

import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.repository.ProjectRepository;
import com.lec.spring.domains.recruitment.dto.RecruitmentPostCommentsDTO;
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

import java.util.HashMap;
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
    public ResponseEntity<Map<String, Object>> applyToProject(
            @PathVariable Long projectId,
            @RequestBody Map<String, Object> request) {
        try {
            System.out.println("📌 [DEBUG] 프로젝트 ID: " + projectId);
            System.out.println("📌 [DEBUG] 요청 데이터: " + request);

            if (!request.containsKey("userId")) {
                System.out.println("[ERROR] userId 값이 요청에 없음");
                throw new IllegalArgumentException("userId 값이 필요합니다.");
            }

            Long userId = Long.parseLong(request.get("userId").toString());
            System.out.println("[DEBUG] userId: " + userId);

            recruitmentPostService.applyToProject(projectId, userId);

            System.out.println("[SUCCESS] 프로젝트 신청 완료");

            // JSON 형식으로 응답 반환
            Map<String, Object> response = new HashMap<>();
            response.put("message", "프로젝트 신청이 완료되었습니다.");
            response.put("projectId", projectId);
            response.put("userId", userId);
            response.put("status", "WAITING"); // 대기 상태 추가 가능

            return ResponseEntity.ok(response);

        } catch (NumberFormatException e) {
            System.out.println("[ERROR] userId 값이 올바르지 않음: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "userId 값이 올바르지 않습니다."));
        } catch (EntityNotFoundException e) {
            System.out.println("[ERROR] 해당 프로젝트 또는 유저가 존재하지 않음: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "해당 프로젝트 또는 유저가 존재하지 않습니다."));
        } catch (Exception e) {
            System.out.println("[ERROR] 서버 오류 발생: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "서버 오류 발생"));
        }
    }


    // 캡틴 권한이 있는 프로젝트 조회
    @GetMapping("/projects/{userId}/captain")
    public ResponseEntity<List<Project>> getCaptainProjects(@PathVariable Long userId) {
        List<Project> captainProjects = projectRepository.findAllByCaptainUser(userId);
        return ResponseEntity.ok(captainProjects);
    }

    // 특정 유저가 작성한 모집글 조회
    @GetMapping("/recruitments/user/{userId}")
    public ResponseEntity<List<RecruitmentPostCommentsDTO>> getUserRecruitmentPosts(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "row", required = false, defaultValue = "0") int row) {

        List<RecruitmentPostCommentsDTO> posts;

        if (row > 0) {
            posts = recruitmentPostService.getUserRecruitmentPostsWithLimit(userId, row);
        } else {
            posts = recruitmentPostService.getUserRecruitmentPosts(userId);
        }

        return ResponseEntity.ok(posts);
    }
}

