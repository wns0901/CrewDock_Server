package com.lec.spring.domains.banner.service;

import com.lec.spring.domains.banner.entity.Banner;

import java.util.List;

public interface BannerService {

    //베너 전체 가져오기
    List<Banner> allBanner (Long id);
}
