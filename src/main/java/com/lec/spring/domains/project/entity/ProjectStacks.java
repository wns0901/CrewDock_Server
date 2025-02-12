package com.lec.spring.domains.project.entity;

import com.lec.spring.domains.stack.entity.Stack;
import com.lec.spring.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString(callSuper = true)
public class ProjectStacks extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Stack stack;

    @Column(name = "project_id")
    @JsonIgnore
    @ToString.Exclude
    private Long projectId;
}
