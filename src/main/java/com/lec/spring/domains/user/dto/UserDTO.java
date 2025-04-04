package com.lec.spring.domains.user.dto;

import com.lec.spring.global.common.entity.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private String nickname;
    private String phoneNumber;
    private Position hopePosition;
    private String profileImgUrl;

}
