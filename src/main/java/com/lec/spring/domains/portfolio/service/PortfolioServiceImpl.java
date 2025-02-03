package com.lec.spring.domains.portfolio.service;

import com.lec.spring.domains.portfolio.entity.Portfolio;
import com.lec.spring.domains.portfolio.entity.PortfolioStack;
import com.lec.spring.domains.portfolio.entity.dto.PortfolioDto;
import com.lec.spring.domains.portfolio.entity.dto.PortfolioStackDto;
import com.lec.spring.domains.portfolio.repository.PortfolioRepository;
import com.lec.spring.domains.portfolio.repository.PortfolioStackRepository;
import com.lec.spring.domains.stack.entity.Stack;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioStackService portfolioStackService;

    @Override
    public List<PortfolioDto> getPortfoliosWithStacks(Long userId) {
        List<Portfolio> portfolios = portfolioRepository.findByUserIdWithStacksQueryDSL(userId);
        return portfolios.stream().map(PortfolioDto::new).collect(Collectors.toList());
    }

    @Override
    public List<PortfolioDto> getUserPortfoliosWithLimit(Long userId, int row) {
        List<Portfolio> portfolios = portfolioRepository.findByUserIdWithLimitQueryDSL(userId, row);
        return portfolios.stream().map(PortfolioDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PortfolioDto createPortfolio(PortfolioDto portfolioDto) {
        Portfolio portfolio = Portfolio.builder()
                .title(portfolioDto.getTitle())
                .content(portfolioDto.getContent())
                .user(portfolioDto.getUser())
                .portfolioStack(new ArrayList<>())
                .build();

        Portfolio savedPortfolio = portfolioRepository.save(portfolio);

        if (portfolioDto.getPortfolioStacks() != null && !portfolioDto.getPortfolioStacks().isEmpty()) {
            List<PortfolioStack> savedStacks = portfolioStackService.createPortfolioStacks(savedPortfolio, portfolioDto.getPortfolioStacks());
            savedPortfolio.getPortfolioStack().addAll(savedStacks);  // ✅ Portfolio 객체에도 추가
        }

        return new PortfolioDto(savedPortfolio);
    }

    @Override
    @Transactional
    public PortfolioDto updatePortfolio(Long portfolioId, PortfolioDto portfolioDto) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("포트폴리오를 찾을 수 없음: " + portfolioId));

        portfolio.setTitle(portfolioDto.getTitle());
        portfolio.setContent(portfolioDto.getContent());

        final Portfolio savedPortfolio = portfolioRepository.save(portfolio);

        portfolioStackService.deleteByPortfolioId(savedPortfolio.getId());

        if (portfolioDto.getPortfolioStacks() != null && !portfolioDto.getPortfolioStacks().isEmpty()) {
            portfolioStackService.createPortfolioStacks(savedPortfolio, portfolioDto.getPortfolioStacks());
        }

        return new PortfolioDto(savedPortfolio);
    }



    @Override
    @Transactional
    public void deletePortfolio(Long portfolioId) {
        portfolioStackService.deleteByPortfolioId(portfolioId);
        portfolioRepository.deleteByPortfolioId(portfolioId);
    }
}