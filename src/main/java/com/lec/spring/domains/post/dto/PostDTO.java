package com.lec.spring.domains.post.dto;

import com.lec.spring.domains.post.entity.Post;
import lombok.*;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class PostDTO extends Post {
    private Long userId;
    private Long projectId;
    private String userNickname;

    public PostDTO(Post post) {
        this.setId(post.getId());
        this.setTitle(post.getTitle());
        this.setContent(post.getContent());
        this.setCreatedAt(post.getCreatedAt());
        this.setUserNickname(post.getUser().getNickname());
        this.setProjectId(post.getProject() != null ? post.getProject().getId() : null);
        this.setUserId(post.getUser().getId());
        this.setCategory(post.getCategory());
        this.setDirection(post.getDirection());
    }


    private List<Map<String, Object>> filteredComments;
}
