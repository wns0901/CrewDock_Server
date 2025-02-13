package com.lec.spring.global.config.security.oauth;

import com.lec.spring.domains.user.entity.Auth;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.entity.UserAuth;
import com.lec.spring.domains.user.repository.AuthRepository;
import com.lec.spring.domains.user.repository.UserAuthRepository;
import com.lec.spring.domains.user.repository.UserRepository;
import com.lec.spring.global.config.security.PrincipalDetails;
import com.lec.spring.global.config.security.oauth.provider.GitHubUserInfo;
import com.lec.spring.global.config.security.oauth.provider.GoogleUserInfo;
import com.lec.spring.global.config.security.oauth.provider.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final UserAuthRepository userAuthRepository;

    @Value("${app.oauth2.password}")
    private String oauth2Password;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String accessToken = userRequest.getAccessToken().getTokenValue();

        OAuth2UserInfo oAuth2UserInfo = switch (provider.toLowerCase()) {
            case "google" -> new GoogleUserInfo(oAuth2User.getAttributes());
            case "github" -> new GitHubUserInfo(oAuth2User.getAttributes(), accessToken);
            default -> throw new IllegalStateException("Unexpected value: " + provider.toLowerCase());
        };

        String providerId = oAuth2UserInfo.getProviderId();
        String username = oAuth2UserInfo.getEmail();
        String password = oauth2Password;
        String name = oAuth2UserInfo.getName();
        String githubUrl = oAuth2UserInfo.getGithubURL();

        User user = userRepository.findByUsername(username);

        if(user == null) {
            User newUser = User.builder()
                    .username(username)
                    .password(password)
                    .name(name)
                    .provider(provider)
                    .providerId(providerId)
                    .githubUrl(githubUrl)
                    .build();
            newUser = userRepository.save(newUser);

            Auth memberAuth = authRepository.findByName("ROLE_MEMBER");

            UserAuth userAuth = UserAuth.builder()
                    .userId(newUser.getId())
                    .auth(memberAuth)
                    .build();

            userAuthRepository.save(userAuth);

            if (newUser != null) {
                user = userRepository.findByUsername(username);  // 다시 읽어와야 한다. id, regDate 등의 정보
            } else {
                throw new OAuth2AuthenticationException("OAuth2 회원가입 실패");
            }
        }

        PrincipalDetails principalDetails = new PrincipalDetails(user, oAuth2User.getAttributes());

        return principalDetails;
    }
}
