package com.lec.spring.domains.stack.controller;

import com.lec.spring.domains.stack.service.StackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stacks")
@RequiredArgsConstructor
public class StackController {


    private final StackService stackService;

    @GetMapping("/all")
    public ResponseEntity<List<String>> getAllStacks() {
        List<String> stackNames = stackService.getAllStackNames();
        return ResponseEntity.ok(stackNames);
    }

}
