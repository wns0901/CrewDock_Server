package com.lec.spring.global.common.util.s3;

import com.lec.spring.global.common.util.BucketDirectory;
import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    String uploadFile(MultipartFile file, BucketDirectory bucketDirectory);

    String uploadImgFile(MultipartFile file, BucketDirectory bucketDirectory);

    String getFileName(String url);

    void deleteFile(String url);
}
