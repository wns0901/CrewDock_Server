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

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final GitService gitService;

    @GetMapping("/projects/{projectId}/githubs")
    public Mono<ResponseEntity<GitDataDTO>> getGitData(@PathVariable Long projectId, @RequestParam("gitURL") String gitUrl) {

        String[] ownerAndRepo;
        try{
            ownerAndRepo = GitUtil.extractOwnerAndRepo(gitUrl);
        } catch (IllegalArgumentException e) {
            return Mono.just(ResponseEntity.badRequest().body(null));
        }
        String owner = ownerAndRepo[0];
        String repo = ownerAndRepo[1];
        return gitService.getGitHubData(owner,repo)
                .map(gitDataDTO -> ResponseEntity.ok().body(gitDataDTO))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
