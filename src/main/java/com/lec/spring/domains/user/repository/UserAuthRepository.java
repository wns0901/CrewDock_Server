package com.lec.spring.domains.user.repository;

import com.lec.spring.domains.user.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
}
