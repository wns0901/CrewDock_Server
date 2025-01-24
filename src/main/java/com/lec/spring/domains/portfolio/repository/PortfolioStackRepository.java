package com.lec.spring.domains.portfolio.repository;

import com.lec.spring.domains.portfolio.entity.PortfolioStack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioStackRepository extends JpaRepository<PortfolioStack, Long> {
}
