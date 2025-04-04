package com.lec.spring.global.config.security.oauth.provider;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class GitHubUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;
    private String accessToken;

    public GitHubUserInfo(Map<String, Object> attributes, String accessToken) {
        this.attributes = attributes;
        this.accessToken = accessToken;
    }

    @Override
    public String getProvider() {
        return "github";
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getEmail() {
        String email = (String) attributes.get("email");
        if (email == null) {
            email = fetchEmailFromGitHub();
        }
        return email;
    }

    @Override
    public String getName() {
        return (String) attributes.get("login");
    }

    @Override
    public String getGithubURL() {
        return (String) attributes.get("html_url");
    }

    private String fetchEmailFromGitHub() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.github.com/user/emails";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setAccept(List.of(MediaType.valueOf("application/vnd.github+json")));

        HttpEntity entity = new HttpEntity(headers);

        List<Map<String, Object>> emails = restTemplate.exchange(url, HttpMethod.GET, entity, List.class).getBody();
        for (Map<String, Object> email : emails) {
            if ((Boolean) email.get("primary") && (Boolean) email.get("verified")) {
                return (String) email.get("email");
            }
        }
        return null;
    }
}
