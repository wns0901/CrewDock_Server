package com.lec.spring.domains.stack.service;

import com.lec.spring.domains.stack.entity.Stack;
import com.lec.spring.domains.stack.repository.StackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StackServiceImpl implements StackService {
  @Autowired
   private StackRepository stackRepository;

    @Override
    public List<Stack> getAllStacks() {
        return stackRepository.findAll();
    }

    @Override
    public List<String> getAllStackNames() {
        return stackRepository.findAll()
                .stream()
                .map(stack -> stack.getName()) // 스택의 name만 리스트로 변환
                .collect(Collectors.toList());
    }
}
