package com.lec.spring.domains.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class CommitDTO {
    String sha;

    boolean isFirstUrl = true;
    public void setIsFirstUrl(boolean isFirstUrl) {
        this.isFirstUrl = isFirstUrl;
    }

    private CommitDetails commit;

}

@Data
class CommitDetails {
    // commit.message
    @JsonProperty("message")
    String message;

    // commit.author 필드를 별도 DTO로 처리
    private Author author;

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
