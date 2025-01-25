package com.lec.spring.domains.project.controller;

import com.lec.spring.domains.project.dto.GitDataDTO;
import com.lec.spring.domains.project.service.GitService;
import com.lec.spring.domains.project.util.GitUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final GitService gitService;

    @GetMapping("/projects/{projectId}/githubs")
    public Mono<ResponseEntity<List<GitDataDTO>>> getGitData(@PathVariable Long projectId,
                                                             @RequestParam List<String> gitURL) {

        return gitService.getGitDataFromUrls(gitURL)
                .map(gitDataDTOList -> ResponseEntity.ok().body(gitDataDTOList))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}

