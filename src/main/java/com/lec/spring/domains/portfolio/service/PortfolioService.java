package com.lec.spring.domains.portfolio.service;

import com.lec.spring.domains.portfolio.entity.Portfolio;
import com.lec.spring.domains.portfolio.entity.dto.PortfolioDto;

import java.util.List;

public interface PortfolioService {

    // 특정 유저의 포트폴리오와 스택을 조회
    List<PortfolioDto> getPortfoliosWithStacks(Long userId);

    // 유저의 포트폴리오 조회 (row 제한 있음)
    List<PortfolioDto> getUserPortfoliosWithLimit(Long userId, int row);

    // 유저의 포트폴리오 작성
    PortfolioDto createPortfolio(PortfolioDto portfolioDto);

    // 유저 포트폴리오 수정
    PortfolioDto updatePortfolio(Long portfolioId, PortfolioDto portfolioDto);

    // 유저 포트폴리오 삭제
    void deletePortfolio(Long portfolioId);


}
