package com.lec.spring.domains.recruitment.dto;

import com.lec.spring.domains.recruitment.entity.RecruitmentComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentCommentsDTO extends RecruitmentComment {
    Long userId;
    String userName;
    Long parentCommentId;
    Boolean isDeleted;

    public static RecruitmentCommentsDTO of(RecruitmentComment comment) {
        RecruitmentCommentsDTO commentDTO = new RecruitmentCommentsDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setUserId(comment.getUser().getId());
        commentDTO.setUserName(comment.getUser().getUsername());
        commentDTO.setContent(comment.getContent());
        commentDTO.setIsDeleted(comment.getDeleted());
        commentDTO.setCreatedAt(comment.getCreatedAt());
        commentDTO.setParentCommentId(comment.getComment() != null ? comment.getComment().getId() : null);
        commentDTO.setIsDeleted(comment.getDeleted());
        return commentDTO;
    }
}
