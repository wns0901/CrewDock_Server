package com.lec.spring.domains.portfolio.entity.dto;

import com.lec.spring.domains.portfolio.entity.PortfolioStack;
import com.lec.spring.domains.stack.entity.Stack;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PortfolioStackDto {
    private Long id;
    private String stackName;
    private Long portfolioId;

    public PortfolioStackDto(PortfolioStack portfolioStack) {
        this.id = portfolioStack.getId();
        this.stackName = portfolioStack.getStack().getName();
        this.portfolioId = portfolioStack.getPortfolio();
    }




}