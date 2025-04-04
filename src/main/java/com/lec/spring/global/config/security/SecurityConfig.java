package com.lec.spring.global.config.security;

import com.lec.spring.domains.chat.repository.ChatRoomRepository;
import com.lec.spring.domains.project.repository.ProjectRepository;
import com.lec.spring.domains.user.repository.AuthRepository;
import com.lec.spring.domains.user.repository.UserRepository;
import com.lec.spring.global.config.security.jwt.JWTFilter;
import com.lec.spring.global.config.security.jwt.JWTUtil;
import com.lec.spring.global.config.security.jwt.LoginFilter;
import com.lec.spring.global.config.security.oauth.CustomOAuth2SuccessHandler;
import com.lec.spring.global.config.security.oauth.PrincipalOauth2UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTUtil jwtUtil;
    private final AuthRepository authRepository;
    private final ProjectRepository projectRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserRepository userRepository;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
    private final PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Value("${cors.allowed-origins}")
    private List<String> corsAllowedOrigins;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // csrf disable
        http
                .csrf(auth -> auth.disable());

        // Form 인증방식 disable
        http
                .formLogin((auth) -> auth.disable());

        // http basic 인증방식 disable
        http
                .httpBasic((auth) -> auth.disable());

        // 경로별 인가 설정
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );

        // 세션 설정
        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        // CORS 설정
        http
                .cors(corsConfigurer -> corsConfigurer.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    // ✅ 방법 1: 구체적인 오리진 지정
//                    config.setAllowedOrigins(corsAllowedOrigins);
                    // ✅ 방법 2: 패턴 사용
                     config.setAllowedOriginPatterns(List.of("*"));
                    config.setAllowedMethods(List.of("*"));
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(List.of("*"));
                    config.setExposedHeaders(List.of("*"));
                    return config;
                }));

        http.addFilterAt(new LoginFilter(jwtUtil, chatRoomRepository, authenticationManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(new JWTFilter(jwtUtil, authRepository, projectRepository, userRepository), LoginFilter.class);

        http.oauth2Login(httpSecurityOAuth2LoginConfigurer -> httpSecurityOAuth2LoginConfigurer
                .userInfoEndpoint(userInfoEndpointConfigurer -> userInfoEndpointConfigurer
                        .userService(principalOauth2UserService))
                .successHandler(customOAuth2SuccessHandler)
        );

        return http.build();
    }


}
