package com.lec.spring.domains.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BasePostDTO {
    private Long id;
    private String title;
    private String content;
    private String userName;
    private LocalDateTime createAt;
}

