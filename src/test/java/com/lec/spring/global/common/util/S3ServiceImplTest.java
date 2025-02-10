package com.lec.spring.global.common.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class S3ServiceImplTest {

    @Autowired
    private S3Service s3Service;

    @Test
    void delete() {
        s3Service.deleteFile("https://bukettest9296.s3.ap-northeast-2.amazonaws.com/POST/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7+2024-06-05+143300_1739199923150.png");
    }
}