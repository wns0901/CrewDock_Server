package com.lec.spring.domains.recruitment.entity.DTO;

import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import lombok.Data;

@Data
public class RecruitmentPostDTO {
    private Long id;
    private String title;
    private String content;
    private String recruitedField;
    private int recruitedNumber;
    private String region;
    private String proceedMethod;
    private String userName;
    private String projectName;

    public static RecruitmentPostDTO fromEntity(RecruitmentPost post) {
        RecruitmentPostDTO dto = new RecruitmentPostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setRecruitedField(post.getRecruitedField());
        dto.setRecruitedNumber(post.getRecruitedNumber());
        dto.setRegion(post.getRegion().name());
        dto.setProceedMethod(post.getProceedMethod().name());

        // 유저와 프로젝트가 존재할 경우만 DTO에 값 할당
        if (post.getUser() != null) {
            dto.setUserName(post.getUser().getUsername());
        }
        if (post.getProject() != null) {
            dto.setProjectName(post.getProject().getName());
        }

        return dto;
    }
}

