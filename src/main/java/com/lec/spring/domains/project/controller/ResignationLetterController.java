package com.lec.spring.domains.project.controller;

import com.lec.spring.domains.project.entity.ResignationLetter;
import com.lec.spring.domains.project.service.ResignationLetterServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ResignationLetterController {

    private final ResignationLetterServiceImpl resignationLetterServiceImpl;

    // 모든 탈퇴 신청 내역 조회
    @GetMapping("/{projectId}/resignations")
    public ResponseEntity<List<ResignationLetter>> getResignationLettersByProjectId(@PathVariable Long projectId) {
        List<ResignationLetter> resignationLetters = resignationLetterServiceImpl.getResignationLettersByProjectId(projectId);
        return ResponseEntity.ok(resignationLetters);
    }

    //  탈퇴 사유 작성
    @PostMapping("/{projectId}/resignations/members")
    public ResponseEntity<ResignationLetter> writeResignationLetter(
            @PathVariable Long projectId,
            @RequestParam Long userId,
            @RequestBody String content) {
        ResignationLetter resignationLetter = resignationLetterServiceImpl.writeResignationLetter(projectId, userId, content);
        return ResponseEntity.ok(resignationLetter);
    }

    //  세부 내역
    @GetMapping("/{projectId}/resignations/{resignationId}")
    public ResponseEntity<ResignationLetter> getResignationLetter(
            @PathVariable Long projectId,
            @PathVariable Long resignationId) {
        ResignationLetter resignationLetter = resignationLetterServiceImpl.getResignationLetter(resignationId);
        return ResponseEntity.ok(resignationLetter);
    }
}
