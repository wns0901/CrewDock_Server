package com.lec.spring.global.config.security.jwt;

import com.lec.spring.global.config.security.PrincipalDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = obtainUsername(request);
        String password = obtainPassword(request);

        Authentication token = new UsernamePasswordAuthenticationToken(username.toUpperCase(), password);

        return authenticationManager.authenticate(token);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        PrincipalDetails userDetails = (PrincipalDetails) authResult.getPrincipal();

        Long id = userDetails.getUser().getId();

        String username = userDetails.getUsername();

        String nickname = userDetails.getUser().getNickname();

        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();

        String role = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        String token = jwtUtil.creatJWT(id, username, nickname,role, 3600 * 1000L);

        response.addHeader("Authorization", "Bearer " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
