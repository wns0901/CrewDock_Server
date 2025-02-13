package com.lec.spring.domains.banner.service;

import com.lec.spring.domains.banner.entity.Banner;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BannerService {
    List<Banner> getAllBanners();
    Banner getBannerById(Long id);
    Banner createBanner(Banner banner, MultipartFile file);
    Banner updateBanner(Long id, Banner banner, MultipartFile image);
    void deleteBanner(Long id);
    void updateBannerActivation(Long id, boolean activate);
}
