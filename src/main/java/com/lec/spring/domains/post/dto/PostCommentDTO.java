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
    private String userNickname;
    private Long parentsId;
}
