package com.lec.spring.domains.project.service;

import com.lec.spring.domains.project.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GitService {
    private final WebClient webClient = WebClient.create("https://api.github.com");

    public Mono<GitDataDTO> getGitHubData(String owner, String repo) {
        // 커밋 데이터 가져오기
        Mono<List<CommitDTO>> commits = webClient.get()
                .uri("/repos/{owner}/{repo}/commits", owner, repo)
                .retrieve()
                .bodyToFlux(CommitDTO.class)
                .collectList();


        // 풀 리퀘스트 데이터 가져오기
        Mono<List<PullDTO>> pulls = webClient.get()
                .uri("/repos/{owner}/{repo}/pulls", owner, repo)
                .retrieve()
                .bodyToFlux(PullDTO.class)
                .collectList();

        // 이슈 데이터 가져오기
        Mono<List<IssueDTO>> issues = webClient.get()
                .uri("/repos/{owner}/{repo}/issues/comments", owner, repo)
                .retrieve()
                .bodyToFlux(IssueDTO.class)
                .collectList();


        Mono<List<BranchDTO>> branches = webClient.get()
                .uri("/repos/{owner}/{repo}/branches",owner,repo)
                .retrieve()
                .bodyToFlux(BranchDTO.class)
                .collectList();

        // 통합 DTO에 데이터 담기
        return Mono.zip(commits,pulls,issues,branches)
                .map(data -> {
                    GitDataDTO gitDataDTO = new GitDataDTO();
                    gitDataDTO.setCommits(data.getT1());
                    gitDataDTO.setPulls(data.getT2());
                    gitDataDTO.setIssues(data.getT3());

                    List<CommitDTO> commitList = gitDataDTO.getCommits();
                    for (CommitDTO commit : commitList) {
                        for (BranchDTO branch : gitDataDTO.getBranches()){
                            if (branch.getCommitSha().equals(commit.getSha())){
                                commit.setBranchName(branch.getName());
                                break;
                            }
                        }
                    }

                    return gitDataDTO;
                });



    }

}
