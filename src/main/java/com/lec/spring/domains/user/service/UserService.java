package com.lec.spring.domains.user.service;

import com.lec.spring.domains.user.dto.RegisterDTO;
import com.lec.spring.domains.user.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> isExistsByUsername(String username);

    ResponseEntity<?> isExistsByNickname(String nickname);

    ResponseEntity<?> sendAuthNum(String email);

    ResponseEntity<?> register(RegisterDTO registerDTO);

    ResponseEntity<?> checkAuthNum(String authNum, String email);

    ResponseEntity<?> deleteUser (Long id);
}
