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
    private int userId;
    private String userName; // 유저 아이디
    private String nickName; // 유저 닉네임
    private Long projectId;
    private String projectName;
    private List<String> stackList; //스택 리스트 추가

    public static RecruitmentPostDTO fromEntity(RecruitmentPost post) {
        RecruitmentPostDTO dto = new RecruitmentPostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setRecruitedField(post.getRecruitedField());
        dto.setDeadline(post.getDeadline().toString());
        dto.setRecruitedNumber(post.getRecruitedNumber());
        dto.setRegion(post.getRegion().name());
        dto.setProceedMethod(post.getProceedMethod().name());

        // 유저와 프로젝트가 존재할 경우만 DTO에 값 할당
        if (post.getUser() != null) {
            dto.setId(post.getUser().getId());
            dto.setUserName(post.getUser().getUsername());
            dto.setNickName(post.getUser().getNickname());
        }
        if (post.getProject() != null) {

            dto.setProjectName(post.getProject().getName());
            dto.setProjectId(post.getProject().getId());

            //프로젝트에 연결된 스택 리스트 가져오기
            List<String> stackNames = post.getProject().getStacks().stream()
                    .map(ProjectStacks::getStack) // ProjectStacks에서 Stack 가져오기
                    .map(stack -> stack.getName()) // Stack에서 이름 가져오기
                    .collect(Collectors.toList());

            dto.setStackList(stackNames);
        }

        return dto;
    }
}
