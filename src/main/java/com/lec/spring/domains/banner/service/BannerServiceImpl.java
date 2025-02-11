package com.lec.spring.domains.banner.service;

import com.lec.spring.domains.banner.entity.Banner;
import com.lec.spring.domains.banner.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;

    // 배너 전체 조회
    @Override
    public List<Banner> getAllBanners() {
        return bannerRepository.findAll();
    }

    // 활성화된 배너 조회(TURE값인것들만)
    @Override
    public List<Banner> getActiveBanners() {
        return bannerRepository.findByActivateTrue();
    }

    // 배너 추가
    @Override
    public Banner createBanner(Banner banner) {
        return bannerRepository.save(banner);
    }

    // 배너 수정
    @Override
    public Banner updateBanner(Long id, Banner banner) {
        Banner existingBanner = bannerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 배너가 존재하지 않습니다."));

        existingBanner.setTitle(banner.getTitle());
        existingBanner.setActivate(banner.getActivate());
        existingBanner.setUrl(banner.getUrl());

        return bannerRepository.save(existingBanner);
    }

    // 배너 삭제
    @Override
    public void deleteBanner(Long id) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 배너가 존재하지 않습니다."));

        bannerRepository.delete(banner);
    }
}
