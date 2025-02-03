package com.lec.spring.domains.user.entity;

import com.lec.spring.domains.user.dto.RegisterDTO;
import com.lec.spring.domains.user.repository.UserRepository;
import com.lec.spring.domains.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterDTO user = (RegisterDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "이메일은 필수입니다");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nickname", "닉네임은 필수입니다");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "비밀번호는 필수입니다");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rePassword", "비밀번호 확인은 필수입니다");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "이름은 필수입니다");

        if (userRepository.existsByUsername(user.getUsername())) {
            errors.rejectValue("username", "이미 존재하는 이메일 입니다");
        }

        if (userRepository.existsByNickname(user.getNickname())) {
            errors.rejectValue("nickname", "이미 존재하는 닉네임 입니다");
        }

        if(!user.getPassword().equals(user.getRePassword())) {
            errors.rejectValue("rePassword", "비밀번호와 비밀번호 확인이 일치하지 않습니다");
        }

    }
}
