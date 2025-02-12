package com.lec.spring.global.config.security.jwt;

import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.entity.ProjectMember;
import com.lec.spring.domains.project.entity.ProjectMemberAuthirity;
import com.lec.spring.domains.project.repository.ProjectMemberRepository;
import com.lec.spring.domains.project.repository.ProjectRepository;
import com.lec.spring.domains.user.entity.Auth;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.entity.UserAuth;
import com.lec.spring.domains.user.repository.AuthRepository;
import com.lec.spring.domains.user.repository.UserRepository;
import com.lec.spring.global.config.security.PrincipalDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final AuthRepository authRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if(authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1];

        if(jwtUtil.isExpired(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        Long id = jwtUtil.getId(token);
        String username = jwtUtil.getUsername(token);
        String nickname = jwtUtil.getNickname(token);
        String role = jwtUtil.getRole(token);

        List<UserAuth> userAuths = new ArrayList<>();
        List<ProjectMember> projectMembers = new ArrayList<>();

        Arrays.stream(role.split(",")).toList().forEach(roleName -> {
            if (roleName.startsWith("ROLE_")) {
                Auth auth = authRepository.findByName(roleName);
                userAuths.add(UserAuth.builder()
                        .userId(id)
                        .auth(auth)
                        .build());
            }

            if (roleName.startsWith("PROJECT_")) {
                Long projectId = Long.parseLong(roleName.split("_")[1]);
                String authName = roleName.split("_")[2];
                Project project = projectRepository.findById(projectId).orElse(null);

                User user = userRepository.findById(id).orElse(null);

                projectMembers.add(ProjectMember.builder()
                        .userId(id)
                        .project(project)
                        .authority(ProjectMemberAuthirity.valueOf(authName))
                        .build());
            }


        });

        User user = User.builder()
                .id(id)
                .username(username)
                .nickname(nickname)
                .password("temppassword")  // 임시 비밀번호
                .userAuths(userAuths)
                .projectMembers(projectMembers)
                .build();

        PrincipalDetails userDetails = new PrincipalDetails(user);

        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
