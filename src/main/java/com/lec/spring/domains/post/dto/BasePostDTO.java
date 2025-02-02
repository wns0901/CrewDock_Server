package com.lec.spring.domains.post.dto;

import com.lec.spring.domains.post.entity.PostAttachment;
import com.lec.spring.domains.post.entity.PostComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BasePostDTO {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private List<PostAttachment> attachments;
    private List<PostComment> comments;
    private PostComment parentsId;
    private LocalDateTime createAt;
}

