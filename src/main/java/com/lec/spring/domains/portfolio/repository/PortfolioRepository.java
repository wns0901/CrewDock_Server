package com.lec.spring.domains.portfolio.repository;

import com.lec.spring.domains.portfolio.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}
