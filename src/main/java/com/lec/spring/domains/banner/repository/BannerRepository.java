package com.lec.spring.domains.banner.repository;

import com.lec.spring.domains.banner.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner, Long> {
}
