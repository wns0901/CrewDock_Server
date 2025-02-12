package com.lec.spring.domains.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString(callSuper = true)
public class ProjectIssue extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "writer_id", nullable = false)
    @JsonIgnore
    private User writer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "manager_id", nullable = false)
    @JsonIgnore
    private User manager;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private Project project;

    @Column(nullable = false)
    private String issueName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectIssueStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectIssuePriority priority;

    @Column(nullable = false)
    private LocalDate deadline;

    @Column(nullable = false)
    private LocalDate startline;

    @Column
    private LocalDateTime createAt;

}
