package com.lec.spring.domains.user.service;

import com.lec.spring.domains.stack.entity.Stack;
import com.lec.spring.domains.stack.repository.StackRepository;
import com.lec.spring.domains.user.dto.RegisterDTO;
import com.lec.spring.domains.user.entity.Auth;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.entity.UserAuth;
import com.lec.spring.domains.user.entity.UserStacks;
import com.lec.spring.domains.user.repository.AuthRepository;
import com.lec.spring.domains.user.repository.UserAuthRepository;
import com.lec.spring.domains.user.repository.UserRepository;
import com.lec.spring.domains.user.repository.UserStacksRepository;
import com.lec.spring.global.config.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserAuthRepository userAuthRepository;
    private final AuthRepository authRepository;
    private final JavaMailSender javaMailSender;
    private final UserStacksRepository userStacksRepository;
    private final StackRepository stackRepository;
    private final RedisUtil redisUtil;

    @Value("${spring.mail.username}")
    private String hostEmail;

    @Override
    public ResponseEntity<?> isExistsByUsername(String username) {
        if(!validateEmailFormat(username)) {
            return new ResponseEntity<>("이메일 형식이 아닙니다.", HttpStatus.BAD_REQUEST);
        }

        boolean exists = userRepository.existsByUsername(username);
        if (exists) {
            return new ResponseEntity<>("중복된 아이디입니다." ,HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().body("사용 가능한 아이디입니다.");
    }

    @Override
    public ResponseEntity<?> isExistsByNickname(String nickname) {
        boolean exists = userRepository.existsByNickname(nickname);
        if (exists) {
            return new ResponseEntity<>("중복된 닉네임입니다.", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().body("사용 가능한 닉네입니다.");
    }

    @Override
    public ResponseEntity<?> sendAuthNum(String email) {
        String authCode = createCode();

        try {
            MimeMessagePreparator preparator = mimeMessage -> {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                helper.setTo(email);
                helper.setFrom(hostEmail);
                helper.setSubject("회원가입 인증 코드");
                helper.setText(creatMailContent(authCode), true);
            };

            javaMailSender.send(preparator);

            redisUtil.setData(email, authCode, 180 * 1000L);
        } catch (Exception e) {
            return new ResponseEntity<>("이메일 전송에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> register(RegisterDTO registerDTO) {

        User user = RegisterDTO.of(registerDTO);

        userRepository.save(user);

        if (user.getId() == null) {
            return new ResponseEntity<>("회원가입에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }

        Auth auth = authRepository.findByName("ROLE_MEMBER");

        userAuthRepository.save(UserAuth.builder().userId(user.getId()).auth(auth).build());

        List<Stack> stacks = stackRepository.findAllById(registerDTO.getStackIds());

        List<UserStacks> userStacks = stacks.stream().map(stack -> UserStacks.builder().user(user).stack(stack).build()).toList();

        userStacksRepository.saveAll(userStacks);

        return ResponseEntity.ok().body("회원가입에 성공했습니다.");
    }

    @Override
    public ResponseEntity<?> checkAuthNum(String authNum, String email) {
        redisUtil.getData(email);
        if (authNum.equals(redisUtil.getData(email))) {
            return ResponseEntity.ok().body("인증 성공");
        }

        return new ResponseEntity<>("인증 실패", HttpStatus.BAD_REQUEST);
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body("존재하지 않는 회원입니다.");
        }

        userStacksRepository.deleteAllByUser(user);
        userAuthRepository.deleteAllByUserId(user.getId());

        userRepository.delete(user);

//        userRepository.deleteByUserId(id);

        return ResponseEntity.ok().body("회원 탈퇴에 성공했습니다.");
    }

    private boolean validateEmailFormat(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    private String createCode() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(4);

            switch (index) {
                case 0: key.append((char) ((int) random.nextInt(26) + 97)); break;
                case 1: key.append((char) ((int) random.nextInt(26) + 65)); break;
                default: key.append(random.nextInt(9));
            }
        }
        return key.toString();
    }

    private String creatMailContent(String authCode) {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                </head>
                <body>
                <div style="margin:100px;">
                    <h1> 안녕하세요.</h1>
                    <h1> CODE DOCK 입니다.</h1>
                    <br>
                    <p> 아래 코드를 회원가입 창으로 돌아가 입력해주세요.</p>
                    <br>
                
                    <div align="center" style="border:1px solid black;">
                        <h3 style="color:blue"> 회원가입 인증 코드 입니다. </h3>
                        <div style="font-size:130%""> $authCode </div>
                    </div>
                    <br/>
                </div>
                
                </body>
                </html>
                """.replace("$authCode", authCode);
    }
}
