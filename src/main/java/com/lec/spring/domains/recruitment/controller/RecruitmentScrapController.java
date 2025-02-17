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


    // 스크랩 추가
    @PostMapping("/{recruitmentId}/scrap")
    public ResponseEntity<String> addScrap(@PathVariable Long recruitmentId, @RequestParam Long userId) {
        scrapService.addScrap(userId, recruitmentId);
        return ResponseEntity.ok("Scrap added successfully.");
    }

    // 스크랩 삭제
    @DeleteMapping("/{recruitmentId}/scrap")
    public ResponseEntity<String> removeScrap(@PathVariable Long recruitmentId, @RequestParam Long userId) {
        scrapService.removeScrap(userId, recruitmentId);
        return ResponseEntity.ok("Scrap removed successfully.");
    }


    // 출력
    @GetMapping("/scraps")
    public ResponseEntity<List<ScrappedPostDTO>> getScrappedPosts(
            @RequestParam Long userId,
            @RequestParam(value = "row", required = false, defaultValue = "0") int row) {

        List<ScrappedPostDTO> scrappedPosts = scrapService.getScrappedPosts(userId, row);
        return ResponseEntity.ok(scrappedPosts);
    }
}