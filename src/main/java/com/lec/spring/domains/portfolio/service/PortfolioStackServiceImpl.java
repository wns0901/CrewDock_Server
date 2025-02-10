package com.lec.spring.domains.portfolio.service;

import com.lec.spring.domains.portfolio.entity.Portfolio;
import com.lec.spring.domains.portfolio.entity.PortfolioStack;
import com.lec.spring.domains.portfolio.dto.PortfolioStackDto;
import com.lec.spring.domains.portfolio.repository.PortfolioStackRepository;
import com.lec.spring.domains.stack.entity.Stack;

import com.lec.spring.domains.stack.repository.StackRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PortfolioStackServiceImpl implements PortfolioStackService {

    private final PortfolioStackRepository portfolioStackRepository;
    private final StackRepository stackRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void deleteByPortfolioId(Long portfolioId) {
        portfolioStackRepository.deleteByPortfolioId(portfolioId);
    }

    @Override
    @Transactional
    public List<PortfolioStack> createPortfolioStacks(Portfolio portfolio, List<PortfolioStackDto> stackDtos) {
        List<PortfolioStack> portfolioStacks = stackDtos.stream()
                .map(stackDto -> {

                    Stack stack = stackRepository.findByName(stackDto.getStackName())
                            .orElseThrow(() -> new RuntimeException("존재하지 않는 스택입니다: " + stackDto.getStackName()));


                    return PortfolioStack.builder()
                            .portfolio(portfolio.getId())
                            .stack(stack)
                            .build();
                })
                .collect(Collectors.toList());

        return portfolioStackRepository.saveAll(portfolioStacks);
    }

    @Override
    public List<PortfolioStack> updatePortfolioStacks(Portfolio portfolio, List<PortfolioStackDto> stackDtos) {
        if (stackDtos == null || stackDtos.isEmpty()) {
            return List.of(); // 빈 리스트 반환
        }

        // ✅ 기존 스택 삭제
        portfolioStackRepository.deleteByPortfolioId(portfolio.getId());

        // ✅ 새 스택 추가
        List<PortfolioStack> newPortfolioStacks = stackDtos.stream()
                .map(dto -> {
                    // ✅ 기존 Stack 조회 (StackRepository 활용)
                    Stack stack = stackRepository.findByName(dto.getStackName())
                            .orElseThrow(() -> new RuntimeException("존재하지 않는 스택입니다: " + dto.getStackName()));

                    // ✅ PortfolioStack 생성
                    return PortfolioStack.builder()
                            .portfolio(portfolio.getId())  // ✅ Portfolio 객체 그대로 사용
                            .stack(stack)  // ✅ Stack 객체 그대로 사용
                            .build();
                })
                .collect(Collectors.toList());

        return portfolioStackRepository.saveAll(newPortfolioStacks);
    }
}

