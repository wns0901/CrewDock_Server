package com.lec.spring.domains.user.dto;

import com.lec.spring.domains.user.entity.User;
import com.lec.spring.global.common.entity.Position;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
public class ModifyDTO  {
    private String nickname;
    private String profileImgUrl;
    private String phoneNumber;
    private String password;
    private String githubUrl;
    private String notionUrl;
    private String blogUrl;
    private Position hopePosition;
    private List<Long> stackIds;
    private String selfIntroduction;


}
