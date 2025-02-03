package com.lec.spring.domains.portfolio.repository.dsl;

import com.lec.spring.domains.portfolio.entity.PortfolioStack;

import java.util.List;
import java.util.Optional;

public interface QPortfolioStackRepository {
    void deleteByPortfolioId(Long portfolioId);
    void savePortfolioStacks(Long portfolioId, List<Long> stackIds);
    List<PortfolioStack> findByStackName(String stackName);

}
