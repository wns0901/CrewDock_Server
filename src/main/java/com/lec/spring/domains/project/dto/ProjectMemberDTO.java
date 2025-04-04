package com.lec.spring.domains.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lec.spring.domains.project.entity.ProjectMember;
import com.lec.spring.domains.project.entity.ProjectMemberAuthirity;
import com.lec.spring.domains.project.entity.ProjectMemberStatus;
import com.lec.spring.domains.user.dto.UserDTO;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.global.common.entity.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectMemberDTO {

    private Long id;
    private UserDTO user;
    private ProjectMemberAuthirity authority;
    private ProjectMemberStatus status;
    private Position position;

    public static ProjectMemberDTO fromEntity(ProjectMember projectMember, User user) {
        return ProjectMemberDTO.builder()
                .id(projectMember.getId())
                .user(UserDTO.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .nickname(user.getNickname())
                        .phoneNumber(user.getPhoneNumber())
                        .hopePosition(user.getHopePosition())
                        .profileImgUrl(user.getProfileImgUrl())
                        .build())
                .authority(projectMember.getAuthority())
                .status(projectMember.getStatus())
                .position(projectMember.getPosition())
                .build();
    }

}