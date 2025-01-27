package com.lec.spring.domains.user.repository;

import com.lec.spring.domains.user.entity.UserStacks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStacksRepository extends JpaRepository<UserStacks, Long> {
}
