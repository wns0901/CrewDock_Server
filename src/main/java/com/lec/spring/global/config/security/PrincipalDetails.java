package com.lec.spring.global.config.security;

import com.lec.spring.domains.project.entity.ProjectMember;
import com.lec.spring.domains.user.entity.Auth;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.entity.UserAuth;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user;

    private Map<String, Object> attributes;

    public User getUser() {
        return user;
    }

    // 일반 로그인 (username, password) 용 생성자
    public PrincipalDetails(User user) {
        this.user = user;
    }

    // OAuth 인증용 생성자
    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        List<UserAuth> userAuth = user.getUserAuths();

        List<ProjectMember> projectMembers = user.getProjectMembers();

        userAuth.forEach(auth -> collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return auth.getAuth().getName();
            }

            @Override
            public String toString() {
                return auth.getAuth().getName();
            }
        }));

        projectMembers.forEach(projectMember -> collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "PROJECT_" + projectMember.getProject().getId() + "_" + projectMember.getAuthority().name();
            }

            @Override
            public String toString() {
                return "PROJECT_" + projectMember.getProject().getId() + "_" + projectMember.getAuthority().name();
            }
        }));

        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
