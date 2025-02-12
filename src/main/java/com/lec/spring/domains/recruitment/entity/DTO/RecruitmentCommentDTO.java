package com.lec.spring.domains.recruitment.entity.DTO;

import com.lec.spring.domains.recruitment.entity.RecruitmentComment;
import lombok.Data;

@Data
public class RecruitmentCommentDTO {
    private Long id;
    private String content;
    private String userName; // 사용자 이름
    private Long parentCommentId; // 부모 댓글 ID

    public static RecruitmentCommentDTO fromEntity(RecruitmentComment comment) {
        RecruitmentCommentDTO dto = new RecruitmentCommentDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setUserName(comment.getUser() != null ? comment.getUser().getUsername() : "알 수 없음");
        dto.setParentCommentId(comment.getComment() != null ? comment.getComment().getId() : null);
        return dto;
    }
}
