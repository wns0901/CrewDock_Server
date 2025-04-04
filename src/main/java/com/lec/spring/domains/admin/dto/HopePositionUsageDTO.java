package com.lec.spring.domains.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HopePositionUsageDTO {
    private String hopePosition;  // 희망 포지션
    private long userCount;      // 해당 포지션에 속한 사용자 수
    private double usagePercentage; // 비율
}