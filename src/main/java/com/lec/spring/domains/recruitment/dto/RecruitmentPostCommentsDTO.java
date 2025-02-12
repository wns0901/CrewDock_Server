package com.lec.spring.domains.recruitment.dto;

import com.lec.spring.domains.recruitment.entity.DTO.RecruitmentCommentDTO;
import com.lec.spring.domains.recruitment.entity.RecruitmentComment;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class RecruitmentPostCommentsDTO {
    private Long id;
    private String title;
    private String content;
    private String recruitedField;
    private int recruitedNumber;
    private LocalDateTime createdAt;
    private String region;
    private String proceedMethod;
    private String userName;
    private String projectName;
    private List<RecruitmentCommentDTO> comments;

    // ✅ 댓글 리스트를 포함한 DTO 변환 메서드
    public static RecruitmentPostCommentsDTO fromEntity(RecruitmentPost post, List<RecruitmentComment> commentList) {
        RecruitmentPostCommentsDTO dto = new RecruitmentPostCommentsDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setContent(post.getContent());
        dto.setRecruitedField(post.getRecruitedField());
        dto.setRecruitedNumber(post.getRecruitedNumber());
        dto.setRegion(post.getRegion().name());
        dto.setProceedMethod(post.getProceedMethod().name());

        if (post.getUser() != null) {
            dto.setUserName(post.getUser().getUsername());
        }

        if (post.getProject() != null) {
            dto.setProjectName(post.getProject().getName());
        }

        // ✅ 댓글 DTO 변환
        dto.setComments(commentList.stream()
                .map(RecruitmentCommentDTO::fromEntity)
                .collect(Collectors.toList()));

        return dto;
    }
}
