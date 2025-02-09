package com.lec.spring.domains.recruitment.entity;

import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class RecruitmentPost extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Region region;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProceedMethod proceedMethod;

    @Column(nullable = false)
    private Integer recruitedNumber;

    @Column(nullable = false)
    private String recruitedField;

    @Column
    private LocalDateTime createAt;
}
