package com.lec.spring.domains.user.service;

import com.lec.spring.domains.user.dto.ModifyDTO;
import com.lec.spring.domains.user.dto.RegisterDTO;
import com.lec.spring.domains.user.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    ResponseEntity<?> isExistsByUsername(String username);

    ResponseEntity<?> isExistsByNickname(String nickname);

    ResponseEntity<?> sendAuthNum(String email);

    ResponseEntity<?> register(RegisterDTO registerDTO);

    ResponseEntity<?> SocialRegister(RegisterDTO registerDTO);

    ResponseEntity<?> checkAuthNum(String authNum, String email);

    ResponseEntity<?> modifyUser(Long id, ModifyDTO modifyDTO);

    ResponseEntity<?> deleteUser (Long id);

    ResponseEntity<?> getUser(Long id);

    ResponseEntity<?> modifyProfileImg(Long userId, MultipartFile file);
}
