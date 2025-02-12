package com.lec.spring.domains.admin.dto;

import com.lec.spring.domains.post.entity.Category;
import com.lec.spring.domains.post.entity.Direction;
import com.lec.spring.domains.post.entity.Post;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PostDTO extends Post {
    Long userId;
    String nickname;
    String name;
    Category category;
    Direction direction;
    Long projectId;
    public PostDTO(Post post) {
        this.setId(post.getId());
        this.setTitle(post.getTitle());
        this.setCreatedAt(post.getCreatedAt());
        this.setUserId(post.getUser().getId());
        this.setNickname(post.getUser().getNickname());
        this.setName(post.getUser().getName());
        this.setCategory(post.getCategory());
        this.setDirection(post.getDirection());
        this.setProjectId(post.getProject().getId());
    }
}
