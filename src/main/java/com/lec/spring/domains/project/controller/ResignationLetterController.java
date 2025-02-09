package com.lec.spring.domains.project.controller;

import com.lec.spring.domains.project.dto.ResignationLetterDTO;
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
    public ResponseEntity<List<ResignationLetterDTO>> getResignationLettersByProjectId(@PathVariable Long projectId) {
        List<ResignationLetterDTO> resignationLetters = resignationLetterServiceImpl.getResignationLettersByProjectId(projectId);
        return ResponseEntity.ok(resignationLetters);
    }

    //  탈퇴 사유 작성
    @PostMapping("/{projectId}/resignations/members")
    public ResponseEntity<ResignationLetterDTO> writeResignationLetter(
            @PathVariable Long projectId,
            @RequestParam Long userId,
            @RequestBody String content) {
        ResignationLetterDTO resignationLetter = resignationLetterServiceImpl.writeResignationLetter(projectId, userId, content);
        return ResponseEntity.ok(resignationLetter);
    }

    //  세부 내역
    @GetMapping("/{projectId}/resignations/{resignationId}")
    public ResponseEntity<ResignationLetterDTO> getResignationLetter(
            @PathVariable Long projectId,
            @PathVariable Long resignationId) {
        ResignationLetterDTO resignationLetter = resignationLetterServiceImpl.getResignationLetter(resignationId);
        return ResponseEntity.ok(resignationLetter);
    }

    @DeleteMapping("/{projectId}/resignations/{resignationId}")
    public ResponseEntity<Void> deleteResignationLetter(
            @PathVariable Long projectId,
            @PathVariable Long resignationId
    ){
        resignationLetterServiceImpl.deleteResignationLetter(resignationId);
        return ResponseEntity.noContent().build();
    }
}
