package com.lec.spring.domains.portfolio.controller;

import com.lec.spring.domains.portfolio.dto.PortfolioDto;
import com.lec.spring.domains.portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    // 유저의 포트폴리오 조회 (row 제한 여부 포함)
    @GetMapping("/{userId}")
    public ResponseEntity<List<PortfolioDto>> getPortfolios(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "row", required = false, defaultValue = "0") int row) {
        List<PortfolioDto> portfolios;

        if (row > 0) {
            portfolios = portfolioService.getUserPortfoliosWithLimit(userId, row);
        } else {
            portfolios = portfolioService.getPortfoliosWithStacks(userId);
        }

        return ResponseEntity.ok(portfolios);
    }

    // 유저의 포트폴리오 작성
    @PostMapping
    public ResponseEntity<PortfolioDto> createPortfolio(@RequestBody PortfolioDto portfolioDto) {
        PortfolioDto createdPortfolio = portfolioService.createPortfolio(portfolioDto);
        return ResponseEntity.ok(createdPortfolio);
    }

    // 유저 포트폴리오 수정
    @PutMapping("/{portfolioId}")
    public ResponseEntity<PortfolioDto> updatePortfolio(
            @PathVariable Long portfolioId,
            @RequestBody PortfolioDto portfolioDto
    ) {
        PortfolioDto updatedPortfolio = portfolioService.updatePortfolio(portfolioId, portfolioDto);
        return ResponseEntity.ok(updatedPortfolio);
    }


    // 유저 포트폴리오 삭제
    @DeleteMapping("/{portfolioId}")
    public ResponseEntity<Void> deletePortfolio(@PathVariable("portfolioId") Long portfolioId) {
        portfolioService.deletePortfolio(portfolioId);
        return ResponseEntity.noContent().build();
    }
}
