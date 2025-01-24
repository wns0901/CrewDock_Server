package com.lec.spring.domains.project.service;

import com.lec.spring.domains.project.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

                    connectBranchNamesToCommits(gitDataDTO);
                    connectBranchNamesToIssues(gitDataDTO);

                    return gitDataDTO;
                });


    }

    private void connectBranchNamesToCommits (GitDataDTO gitDataDTO) {
        List<CommitDTO> commits = gitDataDTO.getCommits();
        List<BranchDTO> branches = gitDataDTO.getBranches();
        for (CommitDTO commit : commits) {
            for (BranchDTO branch : branches) {
                if (branch.getCommitSha().equals(commit.getSha())){
                    commit.setBranchName(branch.getName());
                    break;
                }
            }
        }
    }

    private void connectBranchNamesToIssues (GitDataDTO gitDataDTO) {
        List<BranchDTO> branches = gitDataDTO.getBranches();
        Map<String, String> branchNames = new HashMap<>();

        for (BranchDTO branch : branches) {
            branchNames.put(branch.getName(), branch.getName());
        }

        List<IssueDTO> issues = gitDataDTO.getIssues();
        for (IssueDTO issue : issues) {
            String connectSha = issue.getSha();
            if(connectSha != null && branchNames.containsKey(connectSha)){
                issue.setBranchName(branchNames.get(connectSha));
            }
        }
    }

}
