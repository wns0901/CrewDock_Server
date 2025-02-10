package com.lec.spring.domains.project.service;

import com.lec.spring.domains.project.dto.*;
import com.lec.spring.domains.project.util.GitUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GitService {
    private final WebClient webClient = WebClient.create("https://api.github.com");

    @Value("${github.token}")
    private String token;

    // url 데이터 추출
    public Mono<List<GitDataDTO>> getGitDataFromUrls(List<String> gitUrls) {

        List<Mono<GitDataDTO>> giturlList = gitUrls.stream()
                .map(gitUrl -> {

                    String[] ownerAndRepo = GitUtil.extractOwnerAndRepo(gitUrl);
                    String owner = ownerAndRepo[0];
                    String repo = ownerAndRepo[1];

                    // GitHub 데이터를 가져오는 메서드 호출
                    return getGitHubData(owner, repo)
                            .map(gitDataDTO -> {
                                // 첫 번째 URL이면 isFirstUrl을 true로 설정
                                boolean isFirstUrl = (gitUrls.indexOf(gitUrl) == 0);
                                setIsFirstUrl(gitDataDTO, isFirstUrl);
                                return gitDataDTO;
                            });
                })
                .collect(Collectors.toList());

        // 리스트 반환
        return Mono.zip(giturlList, results -> {
            List<GitDataDTO> gitDataDTOList = new ArrayList<>();
            for (Object result : results) {
                gitDataDTOList.add((GitDataDTO) result);
            }
            return gitDataDTOList;
        });
    }

    private Mono<GitDataDTO> getGitHubData(String owner, String repo) {
        // 커밋 데이터
        Mono<List<CommitDTO>> commits = webClient.get()
                .uri("/repos/{owner}/{repo}/commits", owner, repo)
                .header("Authorization", "token " + token)
                .retrieve()
                .bodyToFlux(CommitDTO.class)
                .collectList();

        // 풀 리퀘스트 데이터
        Mono<List<PullDTO>> pulls = webClient.get()
                .uri("/repos/{owner}/{repo}/pulls", owner, repo)
                .header("Authorization", "token " + token)
                .retrieve()
                .bodyToFlux(PullDTO.class)
                .collectList();

        // 이슈 데이터 가져오기
        Mono<List<IssueDTO>> issues = webClient.get()
                .uri("/repos/{owner}/{repo}/issues", owner, repo)
                .header("Authorization", "token " + token)
                .retrieve()
                .bodyToFlux(IssueDTO.class)
                .collectList();

        // 통합 DTO에 데이터 담기
        return Mono.zip(commits, pulls, issues)
                .map(data -> {
                    GitDataDTO gitDataDTO = new GitDataDTO();
                    gitDataDTO.setCommits(data.getT1());
                    gitDataDTO.setPulls(data.getT2());
                    gitDataDTO.setIssues(data.getT3());

                    return gitDataDTO;
                });
    }

    // isFirstUrl값
    private void setIsFirstUrl(GitDataDTO gitDataDTO, boolean isFirst) {
        // Commit
        for (CommitDTO commit : gitDataDTO.getCommits()) {
            commit.setIsFirstUrl(isFirst);
        }
        // Pulls
        for (PullDTO pull : gitDataDTO.getPulls()) {
            pull.setIsFirstUrl(isFirst);
        }
        // Issue
        for (IssueDTO issue : gitDataDTO.getIssues()) {
            issue.setIsFirstUrl(isFirst);
        }
    }

}
