package com.lec.spring.domains.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.global.common.entity.BaseEntity;
import com.lec.spring.global.common.entity.Position;
import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString(callSuper = true)
public class ProjectMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JsonIgnoreProperties({"password", "userAuths", "userStacks", "projectMembers"})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JsonIgnore
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Enumerated(EnumType.STRING)
    private ProjectMemberAuthirity authority;

    @Enumerated(EnumType.STRING)
    private ProjectMemberStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Position position;

    @JsonProperty("user_info")
    public User getUserInfo() {
        return User.builder()
                .id(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .profileImgUrl(user.getProfileImgUrl())
                .build();
    }
}
