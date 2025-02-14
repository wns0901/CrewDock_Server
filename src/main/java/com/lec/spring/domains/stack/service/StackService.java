package com.lec.spring.domains.stack.service;

import com.lec.spring.domains.stack.entity.Stack;
import com.lec.spring.domains.stack.repository.StackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;



public interface StackService {


    public List<Stack> getAllStacks();


    List<String> getAllStackNames();
}