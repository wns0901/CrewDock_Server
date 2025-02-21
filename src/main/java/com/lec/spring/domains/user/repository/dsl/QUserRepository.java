package com.lec.spring.domains.user.repository.dsl;

import com.lec.spring.domains.user.dto.UserResponseDTO;

public interface QUserRepository {
    void deleteByUserId(Long userId);

    UserResponseDTO getUserWithStacks(Long userId);
}
