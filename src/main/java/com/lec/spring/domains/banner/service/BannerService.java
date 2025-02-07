package com.lec.spring.domains.banner.service;

import com.lec.spring.domains.banner.entity.Banner;

import java.util.List;

public interface BannerService {

    // 배너 전체 조회
    List<Banner> getAllBanners();

    // 활성화된 배너 조회
    List<Banner> getActiveBanners();

    // 너 추가
    Banner createBanner(Banner banner);

    // 배너 수정
    Banner updateBanner(Long id, Banner banner);

    // 배너 삭제
    void deleteBanner(Long id);
}

