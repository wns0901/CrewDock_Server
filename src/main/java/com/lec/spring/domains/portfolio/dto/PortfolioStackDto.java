package com.lec.spring.domains.portfolio.dto;

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

    // ✅ 기존 엔티티 기반 생성자
    public PortfolioStackDto(PortfolioStack portfolioStack) {
        this.id = portfolioStack.getId();
        this.stackName = portfolioStack.getStack().getName();
        this.portfolioId = portfolioStack.getPortfolio();
    }

    // ✅ 새로 추가한 생성자
    public PortfolioStackDto(Long id, Long portfolioId, String stackName) {
        this.id = id;
        this.portfolioId = portfolioId;
        this.stackName = stackName;
    }
}
