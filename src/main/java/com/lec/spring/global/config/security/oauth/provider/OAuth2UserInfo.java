package com.lec.spring.global.config.security.oauth.provider;

public interface OAuth2UserInfo {
    String getProvider();

    String getProviderId();

    String getEmail();

    String getName();

    String getGithubURL();
}
