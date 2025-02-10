package com.lec.spring.domains.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PullDTO {

    String id;

    String title;

    @JsonProperty("user.login")
    String authorName;

    @JsonProperty("head.ref")
    String branchName;

    @JsonProperty("created_at")
    LocalDateTime createdAt;

    User user;

    boolean isFirstUrl = true;
    public void setIsFirstUrl(boolean isFirstUrl) {
        this.isFirstUrl = isFirstUrl;
    }

    @Data
    public static class User {
        String login;
    }
}
