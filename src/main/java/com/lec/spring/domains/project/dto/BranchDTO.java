package com.lec.spring.domains.project.dto;

import lombok.Data;

@Data
public class BranchDTO {
    private String name;
    private String commitSha;
}
