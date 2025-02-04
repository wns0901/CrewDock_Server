package com.lec.spring.domains.banner.controller;

import com.lec.spring.domains.banner.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class BannerController {

    private BannerService bannerService;

    // 베너 전체 가져오기
//    @GetMapping("/banners")
//
    // 베너 등록
//    @PostMapping("/banners")
//
    // 배너 수정
//    @PutMapping("/banners")
//
    // 베너 삭제
//    @DeleteMapping("/banners/{bannerId}")
}
