package com.lec.spring.global.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface S3Service {
    String uploadFile(MultipartFile file, BucketDirectory bucketDirectory);

    String uploadImgFile(MultipartFile file, BucketDirectory bucketDirectory);

    void deleteFile(String url);
}
