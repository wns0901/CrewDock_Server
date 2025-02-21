package com.lec.spring.domains.portfolio.repository;

import com.lec.spring.domains.portfolio.entity.PortfolioStack;
import com.lec.spring.domains.portfolio.repository.dsl.QPortfolioStackRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PortfolioStackRepository extends JpaRepository<PortfolioStack, Long>, QPortfolioStackRepository {


}
