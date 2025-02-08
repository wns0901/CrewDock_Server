package com.lec.spring.domains.stack.controller;

import com.lec.spring.domains.stack.entity.Stack;
import com.lec.spring.domains.stack.service.StackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stacks")
public class StackController {

    private final StackService stackService;

    public StackController(StackService stackService) {
        this.stackService = stackService;
    }

    @GetMapping
    public ResponseEntity<List<Stack>> getAllStacks() {
        List<Stack> stacks = stackService.getAllStacks();
        return ResponseEntity.ok(stacks);
    }
}