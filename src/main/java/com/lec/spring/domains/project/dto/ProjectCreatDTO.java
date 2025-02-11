package com.lec.spring.domains.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ProjectCreatDTO {
    private String name;        // 프로젝트명
    private LocalDate startDate; // 시작 날짜
    private Integer period;      // 진행 기간
    private List<Long> stacks;   // 기술 스택 ID 리스트
    private String position;
}
