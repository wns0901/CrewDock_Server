package com.lec.spring.domains.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BranchDTO {
    private String name;
    @JsonProperty("commit_sha")
    private String commitSha;
}
