package com.lec.spring.domains.portfolio.dto;


import com.lec.spring.domains.portfolio.entity.Portfolio;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PortfolioDto {
    private Long id;
    private String title;
    private String content;
    private String userName;

    private PortfolioUserDto user;

    private List<PortfolioStackDto> portfolioStacks = new ArrayList<>();

    public PortfolioDto(Portfolio portfolio) {
        this.id = portfolio.getId();
        this.title = portfolio.getTitle();
        this.content = portfolio.getContent() != null ? portfolio.getContent() : "";
        this.userName = portfolio.getUser().getName();
        this.user = new PortfolioUserDto(portfolio.getUser());
        this.portfolioStacks = portfolio.getPortfolioStack() != null
                ? portfolio.getPortfolioStack().stream()
                .map(PortfolioStackDto::new)
                .collect(Collectors.toList())
                : new ArrayList<>();
    }
}
