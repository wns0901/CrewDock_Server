package com.lec.spring.domains.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IssueDTO {

    String sha;

    String title;

    @JsonProperty("user.login")
    String authorName;

    @JsonProperty("created_at")
    LocalDateTime createdAt;

    String branchName;

    boolean isFirstUrl;
}
