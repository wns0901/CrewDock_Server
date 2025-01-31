package com.lec.spring.domains.project.service;

import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.entity.ProjectIssue;
import com.lec.spring.domains.project.entity.ProjectIssuePriority;
import com.lec.spring.domains.project.entity.ProjectIssueStatus;
import com.lec.spring.domains.project.repository.ProjectIssueRepository;
import com.lec.spring.domains.project.repository.ProjectRepository;
import com.lec.spring.domains.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProjectIssueServiceImplTest {

    @Mock
    private ProjectIssueRepository projectIssueRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectIssueServiceImpl projectIssueService;

    private Project testProject;
    private ProjectIssue issue1, issue2, issue3;

    @BeforeEach
    void setUp() {
        testProject = new Project();
        testProject.setId(1L);

        // User 객체 생성
        User user1 = new User();
        user1.setUsername("사용자1");

        User user2 = new User();
        user2.setUsername("사용자4");

        User user3 = new User();
        user3.setUsername("사용자3");

        // 날짜 설정
        LocalDate startDate1 = LocalDate.of(2025, 1, 11);
        LocalDate endDate1 = LocalDate.of(2025, 1, 20);

        LocalDate startDate2 = LocalDate.of(2025, 1, 20);
        LocalDate endDate2 = LocalDate.of(2025, 1, 24);

        LocalDate startDate3 = LocalDate.of(2025, 1, 10);
        LocalDate endDate3 = LocalDate.of(2025, 1, 18);

        // 이슈 생성
        issue1 = createIssue(1L, "이슈 1", user1, ProjectIssuePriority.HIGH, ProjectIssueStatus.INPROGRESS, startDate1, endDate1);
        issue2 = createIssue(2L, "이슈 2", user2, ProjectIssuePriority.LOW, ProjectIssueStatus.INPROGRESS, startDate2, endDate2);
        issue3 = createIssue(3L, "이슈 3", user3, ProjectIssuePriority.MIDDLE, ProjectIssueStatus.YET, startDate3, endDate3);
    }

    private ProjectIssue createIssue(long issueId, String issueName, User manager, ProjectIssuePriority priority, ProjectIssueStatus status, LocalDate startLine, LocalDate deadLine) {
        ProjectIssue issue = new ProjectIssue();
        issue.setId(issueId);
        issue.setIssueName(issueName);
        issue.setManager(manager);
        issue.setPriority(priority);
        issue.setStatus(status);
        issue.setStartline(startLine);
        issue.setDeadline(deadLine);
        return issue;
    }

    // ✅ 정렬된 이슈 목록 조회 테스트
    @Test
    void testList() {
        when(projectIssueRepository.findByProjectIdSorted(1L)).thenReturn(Arrays.asList(issue1, issue2, issue3));

        List<ProjectIssue> result = projectIssueService.listByProjectId(1L);

        assertThat(result).isNotEmpty();
        assertThat(result).containsExactly(issue3, issue1, issue2); // 정렬 순서 검증
        verify(projectIssueRepository, times(1)).findByProjectIdSorted(1L);
    }

    // ✅ 이슈 수정 테스트
    @Test
    void testUpdate() {
        User newUser = new User();
        newUser.setUsername("새로운 사용자");

        ProjectIssue updatedIssue = createIssue(1L, "수정된 이슈 1", newUser, ProjectIssuePriority.MIDDLE, ProjectIssueStatus.YET, LocalDate.of(2025, 1, 11), LocalDate.of(2025, 1, 20));

        when(projectIssueRepository.findById(issue1.getId())).thenReturn(Optional.of(issue1));

        int result = projectIssueService.update(1L, issue1.getId(), updatedIssue);

        assertThat(result).isEqualTo(1);
        assertThat(issue1.getIssueName()).isEqualTo("수정된 이슈 1");
        assertThat(issue1.getManager()).isEqualTo(newUser);
        verify(projectIssueRepository, times(1)).save(issue1);
    }

    // ✅ 이슈 삭제 테스트
    @Test
    void testDelete() {
        List<Long> issueIds = Arrays.asList(issue1.getId(), issue2.getId());
        List<ProjectIssue> issues = Arrays.asList(issue1, issue2);

        when(projectIssueRepository.findAllById(issueIds)).thenReturn(issues);

        int deletedCount = projectIssueService.deleteByIds(issueIds);

        assertThat(deletedCount).isEqualTo(2);
        verify(projectIssueRepository, times(1)).deleteAll(issues);
    }
}