package com.lec.spring.domains.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StackUsageDTO {
    private Long stackId;
    private String stackName;
    private Long userCount;
    private Double usagePercentage;
}