package com.lec.spring.domains.portfolio.service;

import com.lec.spring.domains.portfolio.entity.Portfolio;
import com.lec.spring.domains.portfolio.repository.PortfolioRepository;
import com.lec.spring.domains.portfolio.repository.PortfolioStackRepository;

import java.util.List;

public class PortfolioServiceImpl implements PortfolioService {
    private PortfolioRepository portfolioRepository;
    private PortfolioStackRepository portfolioStackRepository;

    public PortfolioServiceImpl(PortfolioRepository portfolioRepository, PortfolioStackRepository portfolioStackRepository) {
        this.portfolioRepository = portfolioRepository;
        this.portfolioStackRepository = portfolioStackRepository;
    }

    @Override
    public List<Portfolio> getUserPortfolios(Long userId) {
        return portfolioRepository.findByUserId(userId);
    }

    @Override
    public List<Portfolio> getPortfoliosWithStacks(Long userId) {
        return List.of();
    }

    @Override
    public List<Portfolio> getUserPortfoliosWithLimit(Long userId, int row) {
        return List.of();
    }

    @Override
    public Portfolio createPortfolio(Portfolio portfolio) {
        return null;
    }

    @Override
    public Portfolio updatePortfolio(Portfolio portfolio, List<Long> stackIds) {
        return null;
    }

    @Override
    public void deletePortfolio(Long userId) {

    }
}
