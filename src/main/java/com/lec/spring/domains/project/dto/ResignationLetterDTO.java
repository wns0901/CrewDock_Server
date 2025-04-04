package com.lec.spring.domains.project.dto;

import com.lec.spring.domains.project.entity.ResignationLetter;
import com.lec.spring.domains.project.entity.ProjectMember;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.repository.UserRepository;  // UserRepository 임포트
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResignationLetterDTO {

    private Long id;
    private Long userId;
    private String content;

    public static ResignationLetterDTO fromEntity(ResignationLetter resignationLetter, Long userId) {
        return ResignationLetterDTO.builder()
                .id(resignationLetter.getId())
                .userId(userId)  // userId 직접 설정
                .content(resignationLetter.getContent())
                .build();
    }

}
