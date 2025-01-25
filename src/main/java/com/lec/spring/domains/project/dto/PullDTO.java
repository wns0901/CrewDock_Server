package com.lec.spring.domains.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PullDTO {

    String sha;

    String title;

    @JsonProperty("user.login")
    String authorName;

    @JsonProperty("head.ref")
    String branchName;

    @JsonProperty("created_at")
    LocalDateTime createdAt;

    boolean isFirstUrl = true;
    public void setIsFirstUrl(boolean isFirstUrl) {
        this.isFirstUrl = isFirstUrl;
    }
}
