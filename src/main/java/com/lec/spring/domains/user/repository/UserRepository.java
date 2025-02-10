package com.lec.spring.domains.user.repository;

import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.repository.dsl.QUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, QUserRepository {
    User findByUsername(String username);
    User findById(long id);

    Boolean existsByUsername(String username);

    Boolean existsByNickname(String nickname);


}
