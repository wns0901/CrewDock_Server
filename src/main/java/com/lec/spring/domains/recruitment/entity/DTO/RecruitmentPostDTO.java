package com.lec.spring.domains.recruitment.entity.DTO;

import com.lec.spring.domains.project.entity.ProjectStacks;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class RecruitmentPostDTO {
    private Long id;
    private String title;
    private String content;
    private String deadline;
    private String recruitedField;
    private int recruitedNumber;
    private String region;
    private String proceedMethod;
    private UserDTO user;
    private ProjectDTO project;

    @Data
    public static class UserDTO {
        private Long userId;  
        private String userName;
        private String nickName;
    }

    @Data
    public static class ProjectDTO {
        private Long projectId;
        private String projectName;
        private List<String> stackList;
    }

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

        // user 객체 설정
        if (post.getUser() != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(post.getUser().getId());
            userDTO.setUserName(post.getUser().getUsername());
            userDTO.setNickName(post.getUser().getNickname());
            dto.setUser(userDTO);
        }

        // project 객체 설정
        if (post.getProject() != null) {
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setProjectId(post.getProject().getId());
            projectDTO.setProjectName(post.getProject().getName());

            // 프로젝트에 연결된 스택 리스트 가져오기
            List<String> stackNames = post.getProject().getStacks().stream()
                    .map(ProjectStacks::getStack)
                    .map(stack -> stack.getName())
                    .collect(Collectors.toList());

            projectDTO.setStackList(stackNames);
            dto.setProject(projectDTO);
        }

        return dto;
    }
}
