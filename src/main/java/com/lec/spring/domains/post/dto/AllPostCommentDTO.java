package com.lec.spring.domains.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllPostCommentDTO {
    List<PostCommentDTO> comments;
    Long count;
}
