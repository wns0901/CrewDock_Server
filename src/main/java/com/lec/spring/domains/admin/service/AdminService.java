package com.lec.spring.domains.admin.service;

import com.lec.spring.domains.admin.dto.HopePositionUsageDTO;
import com.lec.spring.domains.admin.dto.PostDTO;
import com.lec.spring.domains.admin.dto.RecruitmentPostDTO;
import com.lec.spring.domains.admin.dto.StackUsageDTO;
import com.lec.spring.domains.banner.entity.Banner;
import com.lec.spring.domains.banner.repository.BannerRepository;
import com.lec.spring.domains.post.entity.Post;
import com.lec.spring.domains.post.repository.PostRepository;
import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.repository.ProjectRepository;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.repository.RecruitmentPostRepository;
import com.lec.spring.domains.stack.entity.QStack;
import com.lec.spring.domains.stack.entity.Stack;
import com.lec.spring.domains.stack.repository.StackRepository;
import com.lec.spring.domains.user.dto.AdminUserDTO;
import com.lec.spring.domains.user.entity.QUser;
import com.lec.spring.domains.user.entity.QUserStacks;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.repository.UserRepository;
import com.lec.spring.domains.user.repository.UserStacksRepository;
import com.lec.spring.domains.user.service.UserService;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final BannerRepository bannerRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final StackRepository stackRepository;
    private final PostRepository postRepository;
    private final RecruitmentPostRepository recruitmentPostRepository;
    private final UserStacksRepository userStacksRepository;
    private final UserService userService;
    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    public List<Banner> getBanners() {
        return bannerRepository.findAll();
    }

    public List<AdminUserDTO> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> AdminUserDTO.builder() // UserDTO로 변환
                        .id(user.getId())
                        .name(user.getName())
                        .nickname(user.getNickname())
                        .phoneNumber(user.getPhoneNumber())
                        .hopePosition(user.getHopePosition())
                        .profileImgUrl(user.getProfileImgUrl())
                        .createDate(user.getCreatedAt())
                        .email(user.getUsername())

                        .build())
                .collect(Collectors.toList());
    }

    public List<Project> getProjects() {
        return projectRepository.findAll();
    }
    public List<Stack> getStacks() {
        return stackRepository.findAll();
    }

    public List<PostDTO> getPosts() {
        List<Post> all = postRepository.findAll();

        return all.stream().map(PostDTO::new).toList();
    }

    public List<RecruitmentPostDTO> getRecruitmentPosts() {
        List<RecruitmentPost> all = recruitmentPostRepository.findAll();
        return all.stream().map(RecruitmentPostDTO::new).toList();
    }

    public void deleteBanner(Long bannerId) {
        bannerRepository.deleteById(bannerId);
    }
    @Transactional
    public void deleteUser(Long userId) {
        userService.deleteUser(userId);
    }

    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }


    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
    public void deleteRecruitmentPost(Long recruitmentPostId) {
        recruitmentPostRepository.deleteById(recruitmentPostId);
    }

    @Transactional
    public Stack addStack(String name){
        Stack stack = Stack.builder()
                .name(name)
                .build();
        return stackRepository.save(stack);
    }

    @Transactional
    public void deleteStack(Long id){
        if (stackRepository.existsById(id)){
            stackRepository.deleteById(id);
        }else {
            throw new IllegalArgumentException("id 없음");
        }
    }

    public void updateBanner(Banner banner){

    }



    public List<StackUsageDTO> getStackUsagePercentage() {
        QStack stack = QStack.stack;
        QUserStacks userStacks = QUserStacks.userStacks;
        QUser user = QUser.user;

        // userStacks 테이블의 전체 컬럼 수 계산 (전체 스택 수)
        long totalStackCount = queryFactory
                .select(userStacks.count())
                .from(userStacks)
                .fetchOne();

        // 각 스택별 사용 비율 계산
        return queryFactory
                .select(
                        stack.id,
                        stack.name,
                        userStacks.stack.count(),  // 각 스택의 사용 수
                        userStacks.stack.count()
                                .doubleValue()
                                .multiply(100.0)
                                .divide(totalStackCount)  // 사용 비율 계산
                )
                .from(userStacks)
                .join(userStacks.stack, stack)
                .groupBy(stack.id)
                .fetch()
                .stream()
                .map(result -> new StackUsageDTO(
                        result.get(stack.id),  // stackId
                        result.get(stack.name),  // stackName
                        result.get(userStacks.stack.count()),  // userCount
                        result.get(userStacks.stack.count()
                                .doubleValue()
                                .multiply(100.0)
                                .divide(totalStackCount))  // usagePercentage
                ))
                .toList();
    }


    public List<HopePositionUsageDTO> getHopePositionUsagePercentage() {
        List<User> allUsers = userRepository.findAll();
        long totalUsers = allUsers.size();

        // hopePosition이 null인 경우 "미정"으로 처리
        Map<String, Long> hopePositionCount = allUsers.stream()
                .collect(Collectors.groupingBy(user ->
                                user.getHopePosition() != null ? user.getHopePosition().name() : "미정",
                        Collectors.counting()));

        // hopePosition별 비율 계산
        return hopePositionCount.entrySet().stream()
                .map(entry -> new HopePositionUsageDTO(entry.getKey(), entry.getValue(), (entry.getValue() * 100.0) / totalUsers))
                .collect(Collectors.toList());
    }
}
