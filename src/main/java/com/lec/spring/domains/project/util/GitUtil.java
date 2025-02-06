package com.lec.spring.domains.project.util;

public class GitUtil {

    public static String[] extractOwnerAndRepo(String githubUrl) {
        if (githubUrl == null || !githubUrl.startsWith("https://github.com/")) {
            throw new IllegalArgumentException("url 형식 오류:  URL 형식은 https://github.com/{owner}/{repo} 임");
        }

        String formatUrl = githubUrl.replace("https://github.com/", "").replaceAll("/$", "");

        String[] ownerAndRepo = formatUrl.split("/");

        if (ownerAndRepo.length < 2) {
            throw new IllegalArgumentException("url 형식 오류:  URL 형식은 https://github.com/{owner}/{repo} 임");
        }

        return new String[]{ownerAndRepo[0], ownerAndRepo[1]};
    }
}
