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
    private ProjectMemberDTO member;
    private String content;

    // UserRepository를 통해 User 정보를 가져옵니다.
    public static ResignationLetterDTO fromEntity(ResignationLetter resignationLetter, UserRepository userRepository) {
        ProjectMember member = resignationLetter.getMember();

        // userId를 통해 User 엔티티를 조회
        User user = userRepository.findById(member.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // ProjectMemberDTO를 생성
        ProjectMemberDTO memberDTO = ProjectMemberDTO.fromEntity(member, user);

        return ResignationLetterDTO.builder()
                .id(resignationLetter.getId())
                .member(memberDTO)
                .content(resignationLetter.getContent())
                .build();
    }
}
