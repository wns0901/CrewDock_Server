package com.lec.spring.domains.portfolio.dto;

import com.lec.spring.domains.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioUserDto {
    private Long id;
    private String username;
    private String name;


    public PortfolioUserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
    }
}
