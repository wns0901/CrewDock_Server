package com.lec.spring.domains.post.dto;

import com.lec.spring.domains.post.entity.PostComment;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class PostCommentDTO extends PostComment {
    private Long userId;
    private String userName;
    private Long parentCommentId;
    private Boolean isDeleted;

    public static PostCommentDTO of(PostComment comment) {
        PostCommentDTO commentDTO = new PostCommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setPostId(comment.getPostId());
        commentDTO.setUserId(comment.getUser().getId());
        commentDTO.setUserName(comment.getUser().getNickname());
        commentDTO.setContent(comment.getContent());
        commentDTO.setDeleted(comment.getDeleted());
        commentDTO.setFixed(comment.getFixed() != null ? comment.getFixed() : false);
        commentDTO.setCreatedAt(comment.getCreatedAt());
        commentDTO.setParentCommentId(comment.getParentComment() != null ? comment.getParentComment().getId() : null);
        commentDTO.setIsDeleted(comment.getDeleted());
        return commentDTO;
    }
}

