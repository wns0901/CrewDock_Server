package com.lec.spring;

import com.lec.spring.domains.calendar.entity.Calendar;
import com.lec.spring.domains.calendar.repository.CalendarRepository;
import com.lec.spring.domains.portfolio.entity.Portfolio;
import com.lec.spring.domains.portfolio.entity.PortfolioStack;
import com.lec.spring.domains.portfolio.repository.PortfolioRepository;
import com.lec.spring.domains.portfolio.repository.PortfolioStackRepository;
import com.lec.spring.domains.post.entity.*;
import com.lec.spring.domains.post.repository.PostAttachmentRepository;
import com.lec.spring.domains.post.repository.PostCommentRepository;
import com.lec.spring.domains.post.repository.PostRepository;
import com.lec.spring.domains.project.entity.*;
import com.lec.spring.domains.project.repository.ProjectMemberRepository;
import com.lec.spring.domains.project.repository.ProjectRepository;
import com.lec.spring.domains.project.repository.ProjectStacksRepository;
import com.lec.spring.domains.recruitment.entity.ProceedMethod;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.entity.Region;
import com.lec.spring.domains.recruitment.repository.RecruitmentPostRepository;
import com.lec.spring.domains.stack.entity.Stack;
import com.lec.spring.domains.stack.repository.StackRepository;
import com.lec.spring.domains.user.entity.Auth;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.entity.UserAuth;
import com.lec.spring.domains.user.entity.UserStacks;
import com.lec.spring.domains.user.repository.AuthRepository;
import com.lec.spring.domains.user.repository.UserAuthRepository;
import com.lec.spring.domains.user.repository.UserRepository;
import com.lec.spring.domains.user.repository.UserStacksRepository;
import com.lec.spring.global.common.entity.Position;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static com.lec.spring.global.common.entity.Position.*;

@SpringBootTest
public class DummyDataInsertTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMemberRepository projectMemberRepository;

    @Autowired
    private StackRepository stackRepository;

    @Autowired
    private UserStacksRepository userStacksRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private PortfolioStackRepository portfolioStacksRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ProjectStacksRepository projectStacksRepository;

    @Autowired
    private CalendarRepository calendarRepository;

    @Autowired
    private RecruitmentPostRepository recruitmentPostRepository;
    @Autowired
    private PostCommentRepository postCommentRepository;

    @Test
    public void insertDummyData() {
        // 1. 권한 삽입
        Auth authAdmin = authRepository.save(Auth.builder().name("ROLE_ADMIN").build());
        Auth authUser = authRepository.save(Auth.builder().name("ROLE_MEMBER").build());

        // 2. 기술 스택 삽입
        String[] stacks = {
                "JavaScript", "TypeScript", "React", "Java", "Figma", "Vue", "Nodejs", "Spring",
                "Nextjs", "Nestjs", "Express", "Go", "C", "Python", "Django", "Kotlin", "MySQL", "MongoDB",
                "PHP", "GraphQL", "Firebase", "ReactNative", "AWS", "Git", "Docker", "Zeplin"
        };
        List<Stack> stackEntities = Arrays.stream(stacks)
                .map(stack -> stackRepository.save(Stack.builder().name(stack).build()))
                .toList();

        // 3. 유저 생성
        Position[] positions = {BACK, FRONT, FULLSTACK, Position.DESIGNER};
        List<User> users = IntStream.range(1, 9).mapToObj(i ->
                userRepository.save(User.builder()
                        .username("user" + i + "@q.q")
                        .name("사용자" + i)
                        .nickname("닉네임" + i)
                        .password(passwordEncoder.encode("qwer1234"))
                        .phoneNumber("010-1234-567" + i)
                        .hopePosition(positions[i % 4])
                        .build())
        ).toList();

        users.forEach(user -> {
            UserAuth userAuth = UserAuth.builder()
                    .userId(user.getId())
                    .auth(authUser)
                    .build();

            userAuthRepository.save(userAuth);
        });

        User admin = userRepository.save(User.builder()
                .username("admin" + 1 + "@q.q")
                .name("관리자1")
                .nickname("관리자 닉네임")
                .password(passwordEncoder.encode("qwer1234"))
                .phoneNumber("010-1234-9999")
                .build());

        userAuthRepository.save(UserAuth.builder()
                .userId(admin.getId())
                .auth(authAdmin)
                .build());

        userAuthRepository.save(UserAuth.builder()
                .userId(admin.getId())
                .auth(authUser)
                .build());

        List<UserStacks> userStacks = new ArrayList<>();

        for (int j = 0; j < 8; j++) {
            int finalJ = j;
            userStacks.addAll(IntStream.range(0, users.size())
                    .mapToObj(i -> UserStacks.builder()
                            .user(users.get(finalJ))
                            .stack(stackEntities.get(i))
                            .build())
                    .toList());
        }

        userStacksRepository.saveAll(userStacks);

        // 4. 프로젝트 생성
        Project project1 = projectRepository.save(Project.builder()
                .name("프로젝트 A")
                .startDate(LocalDate.of(2025, 1, 1))
                .status(ProjectStatus.CRUISING)
                .period(3)
                .githubUrl1("https://github.com/wns0901/matzipWithYou")
                .introduction("프로젝트 A 소개글입니다.")
                .build());

        List<ProjectStacks> projectStacks = IntStream.range(0,7)
                .mapToObj(i -> ProjectStacks.builder()
                        .projectId(project1.getId())
                        .stack(stackEntities.get(i))
                        .build())
                .toList();

        projectStacksRepository.saveAll(projectStacks);

        Project project2 = projectRepository.save(Project.builder()
                .name("프로젝트 B")
                .period(6)
                .startDate(LocalDate.of(2025, 2, 1))
                .status(ProjectStatus.CRUISING)
                .githubUrl1("https://github.com/jms9901/mytrip")
                .introduction("프로젝트 B 소개글입니다.")
                .build());

        Project project3 = projectRepository.save(Project.builder()
                .name("프로젝트 C")
                .period(0)
                .startDate(LocalDate.of(2025, 3, 1))
                .status(ProjectStatus.CRUISING)
                .githubUrl1("https://github.com/modern-agile-team/5term-main-back")
                .introduction("프로젝트 C 소개글입니다.")
                .build());

        // 5. 프로젝트 멤버 구성
        projectMemberRepository.save(ProjectMember.builder()
                .userId(users.get(0).getId())
                .project(project1)
                .authority(ProjectMemberAuthirity.CAPTAIN)
                .position(BACK)
                .build());

        IntStream.range(1, 5).forEach(i ->
                projectMemberRepository.save(ProjectMember.builder()
                        .userId(users.get(i).getId())
                        .project(project1)
                        .authority(ProjectMemberAuthirity.CREW)
                        .status(ProjectMemberStatus.APPROVE)
                        .position(positions[i % positions.length])
                        .build())
        );

        projectMemberRepository.save(ProjectMember.builder()
                .userId(users.get(5).getId())
                .project(project1)
                .authority(ProjectMemberAuthirity.WAITING)
                .status(ProjectMemberStatus.REQUEST)
                .position(FRONT)
                .build());

        projectMemberRepository.save(ProjectMember.builder()
                .userId(users.get(6).getId())
                .project(project1)
                .authority(ProjectMemberAuthirity.CREW)
                .status(ProjectMemberStatus.WITHDRAW)
                .position(Position.DESIGNER)
                .build());

        // 6. 프로젝트 모집글 생성
        IntStream.range(1, 21).forEach(i ->
                recruitmentPostRepository.save(RecruitmentPost.builder()
                        .user(users.get(i % users.size()))
                        .project(project1)
                        .title("프로젝트 A 개발자 모집 " + i)
                        .content("백엔드/프론트엔드/디자이너 중 1명 모집")
                        .deadline(LocalDate.of(2025, 1, 31))
                        .region(Region.SEOUL)
                        .proceedMethod(ProceedMethod.OFFLINE)
                        .recruitedNumber(3)
                        .recruitedField(String.join(",", BACK.toString(), FRONT.toString(), FULLSTACK.toString())) // 변경된 부분
                        .build())
        );

        // 포트폴리오 삽입
        Portfolio portfolioA = portfolioRepository.save(Portfolio.builder()
                .user(users.get(0))
                .title("포트폴리오 A")
                .content("포트폴리오 A 내용입니다.")
                .build());

        Portfolio portfolioB = portfolioRepository.save(Portfolio.builder()
                .user(users.get(1))
                .title("포트폴리오 B")
                .content("포트폴리오 B 내용입니다.")
                .build());

        // 포트폴리오 기술 스택 삽입
        portfolioStacksRepository.save(PortfolioStack.builder()
                .portfolio(portfolioA.getId())
                .stack(stackEntities.get(3))
                .build());

        portfolioStacksRepository.save(PortfolioStack.builder()
                .portfolio(portfolioA.getId())
                .stack(stackEntities.get(7))
                .build());

        portfolioStacksRepository.save(PortfolioStack.builder()
                .portfolio(portfolioB.getId())
                .stack(stackEntities.get(0))
                .build());

        portfolioStacksRepository.save(PortfolioStack.builder()
                .portfolio(portfolioB.getId())
                .stack(stackEntities.get(2))
                .build());

        // 게시글 삽입
        Post post1 = postRepository.save(Post.builder()
                .user(users.get(0))
                .project(project1)
                .direction(Direction.NOTICE)
                .category(Category.NONE)
                .title("회의록 1")
                .content("회의록 1의 내용입니다.")
                .build());

        Post post2 = postRepository.save(Post.builder()
                .user(users.get(1))
                .project(project2)
                .category(Category.NONE)
                .direction(Direction.REFERENCE)
                .title("참고자료")
                .content("참고자료 내용입니다.")
                .build());

        Post post3 = postRepository.save(Post.builder()
                .user(users.get(2))
                .direction(Direction.FORUM)
                .category(Category.NONE)
                .title("자유 토론")
                .content("자유 토론 내용입니다.")
                .build());


        // 일정 삽입
        calendarRepository.save(Calendar.builder()
                .user(users.get(0))
                .project(project1)
                .content("프로젝트 A 시작 일정")
                .startTime(LocalTime.of(10, 0, 0))
                .endTime(LocalTime.of(18, 0, 0))
                .startDate(LocalDate.of(2025, 1, 1))
                .endDate(LocalDate.of(2025, 1, 31))
                .build());

        calendarRepository.save(Calendar.builder()
                .user(users.get(1))
                .project(project2)
                .content("프로젝트 B 중간 일정")
                .startTime(LocalTime.of(9, 0, 0))
                .endTime(LocalTime.of(17, 0, 0))
                .startDate(LocalDate.of(2025, 2, 1))
                .endDate(LocalDate.of(2025, 2, 28))
                .build());

        // 모집글 삽입
        recruitmentPostRepository.save(RecruitmentPost.builder()
                .user(users.get(0))
                .project(project1)
                .title("프로젝트 A 개발자 모집")
                .content("백엔드 개발자 1명 모집")
                .deadline(LocalDate.of(2025, 1, 31))
                .region(Region.SEOUL)
                .proceedMethod(ProceedMethod.REMOTE)
                .recruitedNumber(1)
                .recruitedField(BACK.toString())
                .build());

        recruitmentPostRepository.save(RecruitmentPost.builder()
                .user(users.get(1))
                .project(project2)
                .title("프로젝트 B 디자이너 모집")
                .content("UI/UX 디자이너 1명 모집")
                .deadline(LocalDate.of(2025, 2, 28))
                .region(Region.GANGWON)
                .proceedMethod(ProceedMethod.OFFLINE)
                .recruitedNumber(1)
                .recruitedField(Position.DESIGNER.toString())
                .build());

        Post targetPost = post1; // 특정 게시글을 지정
        // 댓글 작성
        IntStream.range(1, 5).forEach(j -> {
            PostComment comment = postCommentRepository.save(PostComment.builder()
                    .user(users.get(j))
                    .postId(post1.getId())
                    .content("댓글 " + j)
                    .build());

            // 대댓글 작성
            IntStream.range(1, 3).forEach(k ->
                    postCommentRepository.save(PostComment.builder()
                            .user(users.get((j + k) % users.size()))
                            .postId(post1.getId())
                            .content("대댓글 " + j + "-" + k)
                            .parentComment(comment)
                            .build())
            );
        });
    }
}