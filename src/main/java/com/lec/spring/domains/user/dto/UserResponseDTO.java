package com.lec.spring.domains.user.dto;

import com.lec.spring.domains.user.entity.User;
import com.lec.spring.global.common.entity.Position;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class UserResponseDTO {
    private Long id;
    private String username;
    private String name;
    private String nickname;
    private String profileImgUrl;
    private String phoneNumber;
    private String githubUrl;
    private String notionUrl;
    private String blogUrl;
    private Position hopePosition;
    private String selfIntroduction;
    private List<String> stacks;

    public static UserResponseDTO fromEntity(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .nickname(user.getNickname())
                .profileImgUrl(user.getProfileImgUrl())
                .phoneNumber(user.getPhoneNumber())
                .githubUrl(user.getGithubUrl())
                .notionUrl(user.getNotionUrl())
                .blogUrl(user.getBlogUrl())
                .hopePosition(user.getHopePosition())
                .selfIntroduction(user.getSelfIntroduction())
                .stacks(user.getUserStacks().stream()
                        .map(userStack -> userStack.getStack().getName())
                        .toList())
                .build();
    }
}
