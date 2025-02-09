package com.lec.spring.domains.project.dto;

import lombok.Data;

import java.util.List;

@Data
public class GitDataDTO {

    private List<CommitDTO> commits;
    private List<PullDTO> pulls;
    private List<IssueDTO> issues;

}
