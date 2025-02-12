package com.lec.spring.domains.recruitment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ScrappedPostDTO {
    private Long recruitmentScrapId; // RecruitmentScrap ID
    private Long recruitmentPostId;  // 모집글 ID
    private String title;            // 모집글 제목
    private LocalDateTime createdAt; // 모집글 작성 시간
    private long commentCount;       // 댓글 개수
}
