package com.lec.spring.domains.stack.service;

import com.lec.spring.domains.stack.repository.StackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StackService {

    private final StackRepository stackRepository;

    /**
     * 기술 스택 리스트 가져오기 (이름만 가져오기)
     */
    public List<String> getAllStackNames() {
        return stackRepository.findAll()
                .stream()
                .map(stack -> stack.getName()) // 스택의 name만 리스트로 변환
                .collect(Collectors.toList());
    }
}