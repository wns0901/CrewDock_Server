package com.lec.spring.domains.post.dto;

import com.lec.spring.domains.post.entity.Post;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class PostDTO extends Post {
    private Long userId;
    private Long projectId;
    private String userNickname;
}
