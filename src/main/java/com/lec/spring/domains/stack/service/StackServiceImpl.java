package com.lec.spring.domains.stack.service;

import com.lec.spring.domains.stack.entity.Stack;
import com.lec.spring.domains.stack.repository.StackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StackServiceImpl implements StackService {
  @Autowired
   private StackRepository stackRepository;

    @Override
    public List<Stack> getAllStacks() {
        return stackRepository.findAll();
    }
}
