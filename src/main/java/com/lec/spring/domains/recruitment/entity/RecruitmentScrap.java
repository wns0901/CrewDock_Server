package com.lec.spring.domains.recruitment.entity;

import com.lec.spring.domains.user.entity.User;
import com.lec.spring.global.common.entity.BaseEntity;
import jakarta.persistence.Id;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
@Entity
public class RecruitmentScrap extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "recuritment_id", nullable = false)
    private RecruitmentPost recruitment;
}
