package com.lec.spring.domains.project.repository;

import com.lec.spring.domains.project.entity.ResignationLetter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResignationLetterRepository extends JpaRepository<ResignationLetter, Long> {
}
