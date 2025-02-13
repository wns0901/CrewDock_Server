package com.lec.spring.domains.recruitment.controller;

import com.lec.spring.domains.recruitment.dto.ScrappedPostDTO;
import com.lec.spring.domains.recruitment.entity.RecruitmentScrap;
import com.lec.spring.domains.recruitment.service.RecruitmentScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recruitments")
@RequiredArgsConstructor
public class RecruitmentScrapController {

    private final RecruitmentScrapService scrapService;

    // 모집글 스크랩 추가
    @PostMapping("/{recruitmentsId}/scrap")
    public ResponseEntity<ScrappedPostDTO> scrapPost(
            @PathVariable Long recruitmentsId,
            @RequestParam Long userId) {
        ScrappedPostDTO scrappedPostDTO = scrapService.scrapPost(recruitmentsId, userId);
        return ResponseEntity.ok(scrappedPostDTO);
    }

    // 모집글 스크랩 취소
    @DeleteMapping("/{recruitmentsId}/scrap")
    public ResponseEntity<Void> unScrapPost(
            @PathVariable Long recruitmentsId,
            @RequestParam Long userId) {
        scrapService.unScrapPost(recruitmentsId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/scraps")
    public ResponseEntity<List<ScrappedPostDTO>> getScrappedPosts(
            @RequestParam Long userId,
            @RequestParam(value = "row", required = false, defaultValue = "0") int row) {

        List<ScrappedPostDTO> scrappedPosts = scrapService.getScrappedPosts(userId, row);
        return ResponseEntity.ok(scrappedPosts);
    }
}