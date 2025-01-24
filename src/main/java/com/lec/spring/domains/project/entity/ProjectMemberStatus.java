package com.lec.spring.domains.project.entity;

public enum ProjectMemberStatus {
    REQUEST("요청"), APPROVE("승인"), WITHDRAW("탈퇴");

    private final String label;

    ProjectMemberStatus(String label) { this.label = label; }
}
