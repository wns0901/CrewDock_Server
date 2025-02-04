package com.lec.spring.domains.project.repository;

import com.lec.spring.domains.project.entity.ResignationLetter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResignationLetterRepository extends JpaRepository<ResignationLetter, Long> {
    List<ResignationLetter> findByMemberId(Long memberId);

    List<ResignationLetter> findByMember_Project_Id(Long projectId);
}
