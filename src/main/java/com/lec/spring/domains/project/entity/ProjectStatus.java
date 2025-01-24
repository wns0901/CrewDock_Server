package com.lec.spring.domains.project.entity;

public enum ProjectStatus {
    BOARDING("승선중"),
    CRUISING("순항중"),
    COMPLETED("항해완료"),
    SINKING("난파");

    private final String label;

    ProjectStatus(String label) {
        this.label = label;
    }
}
