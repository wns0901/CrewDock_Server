package com.lec.spring.domains.portfolio.service;

import com.lec.spring.domains.portfolio.entity.Portfolio;
import com.lec.spring.domains.portfolio.entity.PortfolioStack;
import com.lec.spring.domains.portfolio.entity.dto.PortfolioStackDto;

import java.util.List;

public interface PortfolioStackService {
    void deleteByPortfolioId(Long portfolioId);
    List<PortfolioStack> createPortfolioStacks(Portfolio portfolio, List<PortfolioStackDto> stackDtos);
    List<PortfolioStack> updatePortfolioStacks(Portfolio savedPortfolio, List<PortfolioStackDto> portfolioStacks);
}
