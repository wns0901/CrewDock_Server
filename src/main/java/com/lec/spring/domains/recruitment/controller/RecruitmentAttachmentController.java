package com.lec.spring.domains.recruitment.controller;

import com.lec.spring.domains.recruitment.entity.RecruitmentAttachment;
import com.lec.spring.domains.recruitment.service.RecruitmentAttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/recruitments/{recruitmentsId}/attachments")
@RequiredArgsConstructor
public class RecruitmentAttachmentController {

    private final RecruitmentAttachmentService attachmentService;

    // 특정 모집글의 모든 첨부파일 조회
    @GetMapping
    public ResponseEntity<List<RecruitmentAttachment>> getAttachments(@PathVariable Long recruitmentsId) {
        List<RecruitmentAttachment> attachments = attachmentService.findAllByPostId(recruitmentsId);
        return ResponseEntity.ok(attachments);
    }

    // 특정 모집글에 첨부파일 추가 (S3 업로드 방식)
    @PostMapping
    public ResponseEntity<RecruitmentAttachment> uploadAttachment(
            @PathVariable Long recruitmentsId,
            @RequestParam("file") MultipartFile file) {

        RecruitmentAttachment savedAttachment = attachmentService.save(recruitmentsId, file);
        return ResponseEntity.ok(savedAttachment);
    }

    // 특정 첨부파일 삭제 (S3에서도 삭제)
    @DeleteMapping("/{attachmentId}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable Long attachmentId) {
        attachmentService.delete(attachmentId);
        return ResponseEntity.ok().build();
    }
}
