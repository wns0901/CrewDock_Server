package com.lec.spring.domains.user.repository;

import com.lec.spring.domains.user.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    Auth findByName(String name);
}
