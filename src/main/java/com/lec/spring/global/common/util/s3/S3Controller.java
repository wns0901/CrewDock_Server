package com.lec.spring.global.common.util.s3;

import com.lec.spring.global.common.util.BucketDirectory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service S3Service;

    @PostMapping("")
    public ResponseEntity<?> uploadImageFile(@RequestParam MultipartFile file) {
        String url = S3Service.uploadImgFile(file, BucketDirectory.POST);
        return ResponseEntity.ok(url);
    }
}
