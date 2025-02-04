package com.lec.spring.domains.project.service;

import com.lec.spring.domains.project.entity.ProjectMember;
import com.lec.spring.domains.project.entity.ResignationLetter;
import com.lec.spring.domains.project.repository.ProjectMemberRepository;
import com.lec.spring.domains.project.repository.ResignationLetterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResignationLetterServiceImpl implements ResignationLetterService {
    private final ResignationLetterRepository resignationLetterRepository;
    private final ProjectMemberRepository projectMemberRepository;

    // 작성 로직
    public ResignationLetter writeResignationLetter(Long projectId, Long userId, String content) {
        ProjectMember member = projectMemberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("멤버 id 오류"));

        if (!member.getProject().getId().equals(projectId)) {
            throw new IllegalArgumentException("프로젝트에 속해있지 않음");
        }

        ResignationLetter resignationLetter = ResignationLetter.builder()
                .member(member)
                .content(content)
                .build();

        return resignationLetterRepository.save(resignationLetter);
    }

    // 모든 탈퇴 신청 내역
    public List<ResignationLetter> getResignationLettersByProjectId(Long projectId) {
        return resignationLetterRepository.findByMember_Project_Id(projectId);
    }

    // 세부 내역
    public ResignationLetter getResignationLetter(Long resignationId) {
        return resignationLetterRepository.findById(resignationId)
                .orElseThrow(() -> new IllegalArgumentException("id 오류"));
    }

}
