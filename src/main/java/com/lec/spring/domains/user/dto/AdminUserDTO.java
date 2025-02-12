package com.lec.spring.domains.user.dto;

import com.lec.spring.global.common.entity.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUserDTO {
    private Long id;
    private String name;
    private String nickname;
    private String phoneNumber;
    private Position hopePosition;
    private String profileImgUrl;
    private String email;
    private LocalDateTime createDate;

}
