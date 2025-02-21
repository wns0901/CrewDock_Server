package com.lec.spring.domains.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IssueDTO {

    String id;

    String title;


    @JsonProperty("created_at")
    LocalDateTime createdAt;

    User user;  // user 필드를 User 객체로

    boolean isFirstUrl = true;
    public void setIsFirstUrl(boolean isFirstUrl) {
        this.isFirstUrl = isFirstUrl;
    }

    @Data
    public static class User {
        String login;
    }
}
