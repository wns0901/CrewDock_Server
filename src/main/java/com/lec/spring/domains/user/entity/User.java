package com.lec.spring.domains.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lec.spring.domains.project.entity.ProjectMember;
import com.lec.spring.global.common.entity.BaseEntity;
import com.lec.spring.global.common.entity.Position;
import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(length = 15)
    private String phoneNumber;

    @Column()
    private String githubUrl;

    @Column()
    private String notionUrl;

    @Column()
    private String blogUrl;

    @Enumerated(EnumType.STRING)
    private Position hopePosition;

    private String provider;

    private String providerId;

    private String selfIntroduction;

    private String profileImgUrl;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @JsonIgnore
    @Builder.Default
    private List<UserAuth> userAuths = new ArrayList<>();

    public void addUserAuth(UserAuth... userAuths) {
        Collections.addAll(this.userAuths, userAuths);
    }

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @Builder.Default
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private List<ProjectMember> projectMembers = new ArrayList<>();

    public void addProjectMember(ProjectMember... projectMembers) {
        Collections.addAll(this.projectMembers, projectMembers);
    }

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private List<UserStacks> userStacks = new ArrayList<>();
}
