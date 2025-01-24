package com.lec.spring.domains.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommitDTO {

    String sha;

    String message;
    @JsonProperty("commit.author.name")
    String authorName;

    String branchName;

    @JsonProperty("commit.author.date")
    LocalDateTime commitDate;

    boolean isFirstUrl;
}
