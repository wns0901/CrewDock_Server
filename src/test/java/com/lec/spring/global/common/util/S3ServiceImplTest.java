package com.lec.spring.global.common.util;

import com.lec.spring.global.common.util.s3.S3Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class S3ServiceImplTest {

    @Autowired
    private S3Service s3Service;

    @Test
    void delete() {
        s3Service.deleteFile("https://bukettest9296.s3.ap-northeast-2.amazonaws.com/USERPROFILE/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA+2025-01-09+%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE+7.10.14_1739232783032.png");
    }

    @Test
    void getFileName() {
        String url = "https://bukettest9296.s3.ap-northeast-2.amazonaws.com/USERPROFILE/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA+2025-01-09+%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE+7.10.14_1739232783032.png";
        String fileName = s3Service.getFileName(url);
        System.out.println(fileName);
    }
}