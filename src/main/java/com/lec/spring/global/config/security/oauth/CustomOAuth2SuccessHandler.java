package com.lec.spring.global.config.security.oauth;

import com.lec.spring.domains.chat.repository.ChatRoomRepository;
import com.lec.spring.global.config.security.PrincipalDetails;
import com.lec.spring.global.config.security.jwt.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${app.oauth2.auth-redirect-uri}")
    private String oauth2RedirectUri;

    private final JWTUtil jwtUtil;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        PrincipalDetails oauth2User = (PrincipalDetails) authentication.getPrincipal();

        Long id = oauth2User.getUser().getId();
        String username = oauth2User.getUsername();
        String nickname = oauth2User.getUser().getNickname();

        Collection<? extends GrantedAuthority> authorities = oauth2User.getAuthorities();

        String role = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        String chatRoomIds = chatRoomRepository.findChatRoomInfosByUserId(id).stream().map(room -> room.getId().toString()).collect(Collectors.joining(","));

        String token = jwtUtil.creatJWT(id, username, nickname, role, chatRoomIds, 3600*1000L);

        response.addCookie(createCookie("accessToken", token));

        response.addCookie(createCookie("githubUrl", oauth2User.getUser().getGithubUrl()));

        getRedirectStrategy().sendRedirect(request, response, oauth2RedirectUri);
    }

    private Cookie createCookie(String key, String token) {
        Cookie cookie = new Cookie(key, token);
        cookie.setMaxAge(60 * 60 * 60);
        cookie.setPath("/");

        return cookie;
    }


}


















