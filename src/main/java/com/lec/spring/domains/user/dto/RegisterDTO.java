package com.lec.spring.domains.user.dto;

import com.lec.spring.domains.user.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RegisterDTO extends User {
    String rePassword;
    List<Long> stackIds;

    public static User of(RegisterDTO registerDTO) {
        return User.builder()
                .username(registerDTO.getUsername())
                .password(registerDTO.getPassword())
                .name(registerDTO.getName())
                .nickname(registerDTO.getNickname())
                .phoneNumber(registerDTO.getPhoneNumber())
                .githubUrl(registerDTO.getGithubUrl())
                .notionUrl(registerDTO.getNotionUrl())
                .blogUrl(registerDTO.getBlogUrl())
                .hopePosition(registerDTO.getHopePosition())
                .provider(registerDTO.getProvider())
                .providerId(registerDTO.getProviderId())
                .selfIntroduction(registerDTO.getSelfIntroduction())
                .build();
    }
}
