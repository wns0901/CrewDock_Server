package com.lec.spring.domains.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStackDTO {
    private Long userId;
    private Long stackId;
    private String stackName;
}