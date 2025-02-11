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

    @Override
    @Transactional
    public void deleteByPortfolioId(Long portfolioId) {
        QPortfolioStack portfolioStack = QPortfolioStack.portfolioStack;
        queryFactory.delete(portfolioStack)
                .where(portfolioStack.portfolio.eq(portfolioId))
                .execute();
    }

}
