package com.lec.spring.domains.banner.controller;

import com.lec.spring.domains.banner.entity.Banner;
import com.lec.spring.domains.banner.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banners")
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    // 모든 배너 조회 (이제 활성화된 배너만 반환)
    @GetMapping
    public ResponseEntity<List<Banner>> getAllBanners() {
        List<Banner> banners = bannerService.getAllBanners();
        return ResponseEntity.ok(banners);
    }

    // 배너 추가
    @PostMapping
    public ResponseEntity<Banner> createBanner(@RequestBody Banner banner) {
        Banner newBanner = bannerService.createBanner(banner);
        return ResponseEntity.ok(newBanner);
    }

    // 배너 수정
    @PutMapping("/{id}")
    public ResponseEntity<Banner> updateBanner(@PathVariable Long id, @RequestBody Banner banner) {
        Banner updatedBanner = bannerService.updateBanner(id, banner);
        return ResponseEntity.ok(updatedBanner);
    }

    // 배너 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Long id) {
        bannerService.deleteBanner(id);
        return ResponseEntity.ok().build();
    }
}

