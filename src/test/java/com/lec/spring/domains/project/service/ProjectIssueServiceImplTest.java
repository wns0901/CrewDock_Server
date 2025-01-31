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
    @Test
    void setUp() {
        testProject = new Project();
        testProject.setId(1L);

        testProject = new Project();
        testProject.setId(1L);

        // User 객체 생성
        User user1 = new User();
        user1.setUsername("사용자1");

        User user2 = new User();
        user2.setUsername("사용자4");

        User user3 = new User();
        user3.setUsername("사용자3");

        // 날짜 변환
        LocalDate startDate1 = LocalDate.of(2025, 1, 11);
        LocalDate endDate1 = LocalDate.of(2025, 1, 20);

        LocalDate startDate2 = LocalDate.of(2025, 1, 20);
        LocalDate endDate2 = LocalDate.of(2025, 1, 24);

        LocalDate startDate3 = LocalDate.of(2025, 1, 10);
        LocalDate endDate3 = LocalDate.of(2025, 1, 18);

        // 수정된 이슈 생성
        issue1 = createIssue(1L, "이슈 1", user1, ProjectIssuePriority.HIGH, ProjectIssueStatus.INPROGRESS, startDate1, endDate1);
        issue2 = createIssue(2L, "이슈 2", user2, ProjectIssuePriority.LOW, ProjectIssueStatus.INPROGRESS, startDate2, endDate2);
        issue3 = createIssue(3L, "이슈 3", user3, ProjectIssuePriority.MIDDLE, ProjectIssueStatus.YET, startDate3, endDate3);
    }

    // createIssue 메소드
    private ProjectIssue createIssue(long issueId, String issueName, User manager, ProjectIssuePriority projectPriority, ProjectIssueStatus projectStatus, LocalDate startLine, LocalDate deadLine) {
        ProjectIssue issue = new ProjectIssue();

        issue.setId(issueId);
        issue.setIssueName(issueName);
        issue.setManager(manager);
        issue.setPriority(projectPriority);
        issue.setStatus(projectStatus);
        issue.setStartline(startLine);
        issue.setDeadline(deadLine);

        return issue;
    }

    @Test
    void testList() {
        when(projectIssueRepository.findByProjectId(3L)).thenReturn(List.of(issue1, issue2));

        List<ProjectIssue> issues = projectIssueService.listByProjectId(3L);

        assertThat(issues).hasSize(2);
        assertThat(issues.get(0).getIssueName()).isEqualTo("이슈 1");
        assertThat(issues.get(1).getIssueName()).isEqualTo("이슈 2");
        verify(projectIssueRepository, times(1)).findByProjectId(3L);
    }

    @Test
    void testUpdate() {

        // User 객체 생성
        User user1 = new User();
        user1.setUsername("사용자1");

        User user2 = new User();
        user2.setUsername("사용자4");

        User user3 = new User();
        user3.setUsername("사용자3");

        // 날짜 변환
        LocalDate startDate1 = LocalDate.of(2025, 1, 11);
        LocalDate endDate1 = LocalDate.of(2025, 1, 20);

        ProjectIssue updatedIssue = createIssue(1L, "수정된 이슈 1", user2 , ProjectIssuePriority.MIDDLE, ProjectIssueStatus.YET, startDate1, endDate1 );

        when(projectIssueRepository.findById(issue1.getId())).thenReturn(Optional.of(issue1));

        int result = projectIssueService.update(3L, issue1.getId(), updatedIssue);

        assertThat(result).isEqualTo(1);
        assertThat(issue1.getIssueName()).isEqualTo("수정된 이슈 1");
        verify(projectIssueRepository, times(1)).save(issue1);
    }

    @Test
    void testDelete() {
        when(projectIssueRepository.existsById(issue1.getId())).thenReturn(true);
        doNothing().when(projectIssueRepository).deleteById(issue1.getId());

        int result = projectIssueService.deleteById(issue1.getId());

        assertThat(result).isEqualTo(1);
        verify(projectIssueRepository, times(1)).deleteById(issue1.getId());
    }

}