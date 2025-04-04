package com.lec.spring.domains.banner.repository;

import com.lec.spring.domains.banner.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {

    // 활성화된 배너만 조회 (메인 페이지에서 사용)
    List<Banner> findByActivateTrue();
}
