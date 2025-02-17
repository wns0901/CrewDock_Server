package com.lec.spring.domains.recruitment.entity.DTO;

import com.lec.spring.domains.project.entity.ProjectStacks;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

//@NoArgsConstructor
@Data
public class RecruitmentPostDTO {
    private Long id;
    private String title;
    private String content;
    private String deadline;
    private String recruitedField;
    private int recruitedNumber = 1;
    private Integer period;
    private String region;
    private String proceedMethod;
    private UserDTO user;  // UserDTO 포함
    private Long projectId;
    private String projectName;
    private String createdAt;

    // 내부 클래스로 UserDTO 추가
    @Data
    public static class UserDTO {
        private Long userId;
        private String userName;
        private String nickName;

        public UserDTO(Long userId, String userName, String nickName) {
            this.userId = userId;
            this.userName = userName;
            this.nickName = nickName;
        }
    }

    // RecruitmentPost 엔티티를 DTO로 변환하는 메서드
    public static RecruitmentPostDTO fromEntity(RecruitmentPost post) {
        RecruitmentPostDTO dto = new RecruitmentPostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setDeadline(post.getDeadline().toString());
        dto.setRecruitedField(post.getRecruitedField());
        dto.setRecruitedNumber(post.getRecruitedNumber());
        dto.setRegion(post.getRegion().name());
        dto.setProceedMethod(post.getProceedMethod().name());

        // User 정보 설정
        if (post.getUser() != null) {
            dto.setUser(new UserDTO(
                    post.getUser().getId(),
                    post.getUser().getUsername(),
                    post.getUser().getNickname()
            ));
        }

        // Project 정보 설정
        if (post.getProject() != null) {
            dto.setProjectId(post.getProject().getId());
            dto.setProjectName(post.getProject().getName());
            dto.setPeriod(post.getProject().getPeriod());
        }

        return dto;
    }

    // userId 직접 접근 가능하도록 getter 추가
    public Long getUserId() {
        return (user != null) ? user.getUserId() : null;
    }
}
