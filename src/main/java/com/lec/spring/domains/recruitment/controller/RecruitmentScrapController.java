package com.lec.spring.domains.recruitment.controller;

import com.lec.spring.domains.recruitment.service.RecruitmentScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}