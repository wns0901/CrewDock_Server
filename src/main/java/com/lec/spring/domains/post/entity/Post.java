package com.lec.spring.domains.post.entity;

import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
@Entity
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Direction direction;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    @ToString.Exclude
    @Builder.Default
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "post_id")
    private List<PostAttachment> attachments = new ArrayList<>();

    @ToString.Exclude
    @Builder.Default
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "post_id")
    private List<PostComment> comments = new ArrayList<>();
}
