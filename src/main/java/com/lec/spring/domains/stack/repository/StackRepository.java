package com.lec.spring.domains.stack.repository;

import com.lec.spring.domains.stack.entity.Stack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StackRepository extends JpaRepository<Stack, Long> {
    Optional<Stack> findByName(String name);
}
