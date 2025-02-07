package com.lec.spring.domains.project.service;

import com.lec.spring.domains.project.entity.ProjectMember;
import com.lec.spring.domains.project.entity.ResignationLetter;
import com.lec.spring.domains.project.dto.ResignationLetterDTO;
import com.lec.spring.domains.project.repository.ProjectMemberRepository;  // ProjectMemberRepository 임포트
import com.lec.spring.domains.project.repository.ResignationLetterRepository;
import com.lec.spring.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResignationLetterServiceImpl implements ResignationLetterService {
    private final ResignationLetterRepository resignationLetterRepository;
    private final ProjectMemberRepository projectMemberRepository;  // ProjectMemberRepository 추가
    private final UserRepository userRepository;

    // 작성 로직
    public ResignationLetterDTO writeResignationLetter(Long projectId, Long userId, String content) {
        ProjectMember member = projectMemberRepository.findById(userId)  // ProjectMemberRepository 사용
                .orElseThrow(() -> new IllegalArgumentException("멤버 id 오류"));

        if (!member.getProject().getId().equals(projectId)) {
            throw new IllegalArgumentException("프로젝트에 속해있지 않음");
        }

        ResignationLetter resignationLetter = ResignationLetter.builder()
                .member(member)
                .content(content)
                .build();

        ResignationLetter savedResignationLetter = resignationLetterRepository.save(resignationLetter);

        // ResignationLetterDTO로 변환하여 반환
        return ResignationLetterDTO.fromEntity(savedResignationLetter, userRepository);
    }

    // 모든 탈퇴 신청 내역
    public List<ResignationLetterDTO> getResignationLettersByProjectId(Long projectId) {
        List<ResignationLetter> resignationLetters = resignationLetterRepository.findByMember_Project_Id(projectId);

        // ResignationLetterDTO 리스트로 변환하여 반환
        return resignationLetters.stream()
                .map(resignationLetter -> ResignationLetterDTO.fromEntity(resignationLetter, userRepository))
                .toList();
    }

    // 세부 내역
    public ResignationLetterDTO getResignationLetter(Long resignationId) {
        ResignationLetter resignationLetter = resignationLetterRepository.findById(resignationId)
                .orElseThrow(() -> new IllegalArgumentException("id 오류"));

        // ResignationLetterDTO로 변환하여 반환
        return ResignationLetterDTO.fromEntity(resignationLetter, userRepository);
    }

    public void deleteResignationLetter(Long resignationId) {
        if (!resignationLetterRepository.existsById(resignationId)) {
            throw new IllegalArgumentException("존재하지 않는 탈퇴 신청입니다.");
        }
        resignationLetterRepository.deleteById(resignationId);
    }
}
