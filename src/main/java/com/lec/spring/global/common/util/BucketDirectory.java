package com.lec.spring.global.common.util;

public enum BucketDirectory {
    USERPROFILE("profile"),
    PROJECTPROFILE("project-profile"),
    BANNER("banner"),
    POST("post"),
    RECRUITMENT("recruitment"),
    PORTFOLIO("portfolio"),
    PROJECTPOST("project-post");

    private final String directory;

    BucketDirectory(String directory) {
        this.directory = directory;
    }

    public String getDirectory() {
        return directory;
    }
}
