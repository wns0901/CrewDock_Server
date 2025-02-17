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
        // userId를 기반으로 해당 사용자가 프로젝트 멤버인지 확인
        ProjectMember member = projectMemberRepository.findByUserIdAndProject_Id(userId, projectId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로젝트의 멤버가 아닙니다."));

        // 탈퇴 신청 생성
        ResignationLetter resignationLetter = ResignationLetter.builder()
                .member(member)  // memberId가 저장됨
                .content(content)
                .build();

        ResignationLetter savedResignationLetter = resignationLetterRepository.save(resignationLetter);

        // userId를 직접 전달하여 DTO 변환
        return ResignationLetterDTO.fromEntity(savedResignationLetter, userId);
    }
    // 모든 탈퇴 신청 내역
    public List<ResignationLetterDTO> getResignationLettersByProjectId(Long projectId) {
        List<ResignationLetter> resignationLetters = resignationLetterRepository.findByMember_Project_Id(projectId);

        // ResignationLetterDTO 리스트로 변환하여 반환
        return resignationLetters.stream()
                .map(resignationLetter -> {
                    Long userId = resignationLetter.getMember().getUserId();  // 직접 userId 가져오기
                    return ResignationLetterDTO.fromEntity(resignationLetter, userId);
                })
                .toList();
    }


    public ResignationLetterDTO getResignationLetter(Long resignationId) {
        ResignationLetter resignationLetter = resignationLetterRepository.findById(resignationId)
                .orElseThrow(() -> new IllegalArgumentException("id 오류"));

        Long userId = resignationLetter.getMember().getUserId();  // 직접 userId 가져오기

        return ResignationLetterDTO.fromEntity(resignationLetter, userId);
    }



    public void deleteResignationLetter(Long resignationId) {
        if (!resignationLetterRepository.existsById(resignationId)) {
            throw new IllegalArgumentException("존재하지 않는 탈퇴 신청입니다.");
        }
        resignationLetterRepository.deleteById(resignationId);
    }
}
