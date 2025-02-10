package com.lec.spring.domains.project.entity;

import com.lec.spring.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.Check;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "project")
@ToString(callSuper = true)
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Check(constraints = "period IN (1, 3, 6, 0)")
    private Integer period;

    @Column(nullable = false)
    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @Column
    private String githubUrl1;

    @Column
    private String githubUrl2;

    @Column
    private String designUrl;

    @Column
    private String imgUrl;

    @Column(columnDefinition = "LONGTEXT")
    private String introduction;

    @OneToMany(fetch = FetchType.EAGER,  cascade = CascadeType.ALL)
    @Builder.Default
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name = "project_id")
    private List<ProjectStacks> stacks = new ArrayList<>();
}
