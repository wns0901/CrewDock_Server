package com.lec.spring.domains.portfolio.repository.dsl;

import com.lec.spring.domains.portfolio.entity.Portfolio;
import com.lec.spring.domains.portfolio.entity.QPortfolio;
import com.lec.spring.domains.portfolio.entity.QPortfolioStack;
import com.lec.spring.domains.stack.entity.QStack;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor

public class QPortfolioRepositoryImpl implements QPortfolioRepository {
    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    @Override
    public List<Portfolio> findByUserIdWithStacksQueryDSL(Long userId) {
        QPortfolio portfolio = QPortfolio.portfolio;
        QPortfolioStack portfolioStack = QPortfolioStack.portfolioStack;
        QStack stack = QStack.stack;

        return queryFactory
                .selectFrom(portfolio)
                .leftJoin(portfolio.portfolioStack, portfolioStack).fetchJoin()
                .leftJoin(portfolioStack.stack, stack).fetchJoin()  // ✅ 스택까지 가져옴
                .where(portfolio.user.id.eq(userId))
                .fetch();
    }

    @Override
    public List<Portfolio> findByUserIdWithLimitQueryDSL(Long userId, int row) {
        QPortfolio portfolio = QPortfolio.portfolio;
        return queryFactory.selectFrom(portfolio)
                .where(portfolio.user.id.eq(userId))
                .limit(row)
                .fetch();
    }

    @Override
    public List<Portfolio> findByUserIdWithQueryDSL(Long userId) {
        QPortfolio portfolio = QPortfolio.portfolio;
        return queryFactory.selectFrom(portfolio)
                .where(portfolio.user.id.eq(userId))
                .fetch();
    }

    @Override
    @Transactional
    public Portfolio savePortfolio(Portfolio portfolio) {
        entityManager.persist(portfolio);
        return portfolio;
    }

    @Override
    @Transactional
    public void deleteByPortfolioId(Long portfolioId) {
        QPortfolio portfolio = QPortfolio.portfolio;
        queryFactory.delete(portfolio)
                .where(portfolio.id.eq(portfolioId))
                .execute();
    }
}