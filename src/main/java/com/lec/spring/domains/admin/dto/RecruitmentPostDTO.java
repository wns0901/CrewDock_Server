package com.lec.spring.domains.admin.dto;

import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class RecruitmentPostDTO extends RecruitmentPost {
    Long userId;
    String nickname;
    String name;
    String title;



    public RecruitmentPostDTO(RecruitmentPost post) {
        this.setId(post.getId());
        this.setTitle(post.getTitle());
       this.setCreatedAt(post.getCreatedAt());

        if (post.getUser() != null) {
            this.userId = post.getUser().getId();
            this.nickname = post.getUser().getNickname();
            this.name = post.getUser().getName();
        }
    }



}
