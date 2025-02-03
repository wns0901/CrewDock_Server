package com.lec.spring.domains.portfolio.service;

import com.lec.spring.domains.portfolio.entity.Portfolio;
import com.lec.spring.domains.portfolio.entity.PortfolioStack;
import com.lec.spring.domains.portfolio.entity.dto.PortfolioStackDto;
import com.lec.spring.domains.portfolio.repository.PortfolioStackRepository;
import com.lec.spring.domains.stack.entity.Stack;

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
        List<PortfolioStack> portfolioStacks = stackDtos.stream().map(stackDto -> {
            // ✅ 기존 스택 확인
            List<PortfolioStack> existingStacks = portfolioStackRepository.findByStackName(stackDto.getStackName());

            Stack stack;
            if (existingStacks.isEmpty()) {
                // ✅ 새로운 스택 생성 및 저장
                stack = new Stack();
                stack.setName(stackDto.getStackName());
                entityManager.persist(stack);  // ✅ persist() 후 flush() 필요
                entityManager.flush();  // ✅ 영속성 컨텍스트 반영
            } else {
                stack = existingStacks.get(0).getStack();  // ✅ 기존 스택 재사용
            }

            // ✅ PortfolioStack 생성
            return PortfolioStack.builder()
                    .portfolio(portfolio.getId())  // ✅ Portfolio ID 연결
                    .stack(stack)  // ✅ Stack 연결
                    .build();
        }).collect(Collectors.toList());

        // ✅ DB에 저장
        portfolioStackRepository.saveAll(portfolioStacks);  // 🔥 이 코드 추가!
        return portfolioStacks;
    }

}
