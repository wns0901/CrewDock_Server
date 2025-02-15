package com.lec.spring.domains.recruitment.entity.DTO;

import com.lec.spring.domains.recruitment.entity.RecruitmentComment;
import lombok.Data;

@Data
public class RecruitmentCommentDTO {
    private Long id;
    private String content;
    private Long userId;  // userId 추가
    private String userName; // 사용자 이름
    private Long parentCommentId; // 부모 댓글 ID

    public static RecruitmentCommentDTO fromEntity(RecruitmentComment comment) {
        RecruitmentCommentDTO dto = new RecruitmentCommentDTO();
        dto.setId(comment.getId());
        dto.setUserId(comment.getUser() != null ? comment.getUser().getId() : null);
        dto.setContent(comment.getContent());
        dto.setUserName(comment.getUser() != null ? comment.getUser().getNickname() : "알 수 없음");

        Long parentId = comment.getComment() != null ? comment.getComment().getId() : null;
        dto.setParentCommentId(parentId);

        System.out.println("DTO 변환: 댓글 ID = " + dto.getId() + ", 부모 댓글 ID = " + parentId);

        return dto;
    }



}
