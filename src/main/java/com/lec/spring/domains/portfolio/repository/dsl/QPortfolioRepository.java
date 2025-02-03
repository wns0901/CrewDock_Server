package com.lec.spring.domains.portfolio.repository.dsl;

import com.lec.spring.domains.portfolio.entity.Portfolio;

import java.util.List;

public interface QPortfolioRepository {
    List<Portfolio> findByUserIdWithStacksQueryDSL(Long userId);
    List<Portfolio> findByUserIdWithLimitQueryDSL(Long userId, int row);
    void deleteByPortfolioId(Long portfolioId);
}
