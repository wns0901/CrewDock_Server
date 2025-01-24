package com.lec.spring.domains.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommitDTO {
    String sha;

    // 중첩된 Commit 객체를 별도의 DTO로 매핑
    private CommitDetails commit;

    // 기타 필드
    private String branchName;
}

@Data
class CommitDetails {
    // commit.message
    @JsonProperty("message")
    String message;

    // commit.author 필드를 별도 DTO로 처리
    private Author author;

    // 기타 필드
    @JsonProperty("url")
    private String url;
}

@Data
class Author {
    // commit.author.name
    @JsonProperty("name")
    String name;

    // commit.author.date
    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    LocalDateTime date;
}
