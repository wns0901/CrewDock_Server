package com.lec.spring.domains.recruitment.entity;

import com.lec.spring.domains.user.entity.User;
import com.lec.spring.global.common.entity.BaseEntity;
import jakarta.persistence.Id;

import jakarta.persistence.*;


public class RecruitmentScrap extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private RecruitmentPost recuritment;
}
