package com.lec.spring.domains.user.repository;

import com.lec.spring.domains.user.entity.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    void test1() {
        User user = userRepository.findById(1L).orElse(null);
        System.out.println(user);
    }

}