package com.lec.spring.domains.user.controller;

import com.lec.spring.domains.user.dto.ModifyDTO;
import com.lec.spring.domains.user.dto.RegisterDTO;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.entity.UserValidator;
import com.lec.spring.domains.user.service.UserService;
import com.lec.spring.global.config.security.PrincipalDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;

    @GetMapping("/auth")
    public Authentication auth() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication;
    }

    @GetMapping("/check/username")
    public ResponseEntity<?> checkUsername(@RequestParam("username") String username) {
        return userService.isExistsByUsername(username);
    }

    @GetMapping("/check/nickname")
    public ResponseEntity<?> checkNickname(@RequestParam("nickname") String nickname) {
        return userService.isExistsByNickname(nickname);
    }

    @GetMapping("/check/email")
    public ResponseEntity<?> checkEmail(@RequestParam("email") String email) {
        return userService.sendAuthNum(email);
    }

    @GetMapping("/check/authNum")
    public ResponseEntity<?> checkAuthNum(
            @RequestParam("authNum") String authNum,
            @RequestParam("email") String email){
        return userService.checkAuthNum(authNum, email);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO userDto, BindingResult bindingResult) {
        System.out.println(userDto);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getCode).toList());
        }
        return userService.register(userDto);
    }

    @PostMapping("/register/social")
    public ResponseEntity<?> socialRegister(@RequestBody RegisterDTO userDto) {
        return userService.SocialRegister(userDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> modifyUser(@PathVariable Long id, @RequestBody ModifyDTO modifyDTO) {
        return userService.modifyUser(id, modifyDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @InitBinder("registerDTO")
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.addValidators(userValidator);
    }

    @GetMapping("")
    public User user(@AuthenticationPrincipal PrincipalDetails userDetails) {
        return (userDetails != null) ? userDetails.getUser() : null;
    }

    @PatchMapping("/{id}/profile-img")
    public ResponseEntity<?> modifyProfileImg(@PathVariable Long id, MultipartFile file) {
        return userService.modifyProfileImg(id, file);
    }
}
