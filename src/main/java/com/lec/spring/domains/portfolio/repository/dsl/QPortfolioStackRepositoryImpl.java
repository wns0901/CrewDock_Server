package com.lec.spring.domains.portfolio.repository.dsl;

import com.lec.spring.domains.portfolio.entity.PortfolioStack;
import com.lec.spring.domains.portfolio.entity.QPortfolioStack;
import com.lec.spring.domains.portfolio.repository.PortfolioStackRepository;
import com.lec.spring.domains.stack.entity.QStack;
import com.lec.spring.domains.stack.entity.Stack;
import com.lec.spring.domains.stack.repository.dsl.QStackRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class QPortfolioStackRepositoryImpl implements QPortfolioStackRepository {
    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public void deleteByPortfolioId(Long portfolioId) {
        QPortfolioStack portfolioStack = QPortfolioStack.portfolioStack;
        queryFactory.delete(portfolioStack)
                .where(portfolioStack.portfolio.eq(portfolioId))
                .execute();
    }

    @Override
    @Transactional
    public void savePortfolioStacks(Long portfolioId, List<Long> stackIds) {
        List<PortfolioStack> portfolioStacks = stackIds.stream()
                .map(stackId -> PortfolioStack.builder()
                        .portfolio(portfolioId)  // ✅ 저장할 Portfolio ID
                        .stack(Stack.builder().id(stackId).build())  // ✅ Stack 엔터티 매핑
                        .build()
                ).collect(Collectors.toList());

        // ✅ EntityManager 사용하여 저장 (순환 참조 방지)
        for (PortfolioStack portfolioStack : portfolioStacks) {
            entityManager.persist(portfolioStack);
        }
    }

    @Override
    public List<PortfolioStack> findByStackName(String stackName) {
        QPortfolioStack portfolioStack = QPortfolioStack.portfolioStack;
        QStack stack = QStack.stack;

        return queryFactory
                .selectFrom(portfolioStack)
                .join(portfolioStack.stack, stack)
                .where(stack.name.eq(stackName))
                .fetch();
    }
}
