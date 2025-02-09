package com.lec.spring.domains.recruitment.controller;

import com.lec.spring.domains.recruitment.entity.RecruitmentScrap;
import com.lec.spring.domains.recruitment.service.RecruitmentScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recruitments")
@RequiredArgsConstructor
public class RecruitmentScrapController {

    private final RecruitmentScrapService scrapService;

    // 모집글 스크랩 추가
    @PostMapping("/{recruitmentsId}/scrap")
    public ResponseEntity<Void> scrapPost(@PathVariable Long recruitmentsId, @RequestParam Long userId) {
        scrapService.scrapPost(recruitmentsId, userId);
        return ResponseEntity.ok().build();
    }

    // 모집글 스크랩 취소
    @DeleteMapping("/{recruitmentsId}/scrap")
    public ResponseEntity<Void> unScrapPost(@PathVariable Long recruitmentsId, @RequestParam Long userId) {
        scrapService.unScrapPost(recruitmentsId, userId);
        return ResponseEntity.ok().build();
    }

    // 내가 스크랩한 모집글 조회 (row 파라미터 지원)
    @GetMapping("/scraps")
    public ResponseEntity<List<RecruitmentScrap>> getScrappedPosts(
            @RequestParam Long userId,
            @RequestParam(value = "row", required = false, defaultValue = "0") int row) {

        List<RecruitmentScrap> scraps = (row > 0) ?
                scrapService.getScrappedPostsWithLimit(userId, row) :
                scrapService.getScrappedPosts(userId);

        return ResponseEntity.ok(scraps);
    }
}