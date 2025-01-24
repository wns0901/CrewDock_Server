package com.lec.spring.domains.project.entity;

public enum ProjectMemberAuthirity {
    CAPTAIN("캡틴"), CREW("크루"), WAITING("대기");
    private final String label;
    ProjectMemberAuthirity(String label) { this.label = label; }
}
