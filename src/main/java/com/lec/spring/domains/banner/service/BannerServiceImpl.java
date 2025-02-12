package com.lec.spring.domains.banner.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.lec.spring.domains.banner.entity.Banner;
import com.lec.spring.domains.banner.repository.BannerRepository;
import com.lec.spring.global.common.util.BucketDirectory;
import com.lec.spring.global.common.util.S3Service;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {
    private final BannerRepository bannerRepository;
    private final S3Service s3Service;
    @Override
    public List<Banner> getAllBanners() {
        return bannerRepository.findAll();
    }

    @Override
    public Banner getBannerById(Long id) {
        return bannerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 배너를 찾을 수 없습니다. id=" + id));
    }

    @Override
    @Transactional
    public Banner createBanner(Banner banner,MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("배너 이미지는 필수입니다.");
        }

        // S3에 이미지 업로드
        String imgUrl = s3Service.uploadImgFile(image, BucketDirectory.BANNER);

        // 새로운 배너 객체 생성
        Banner bannerEntity = Banner.builder()
                .title(banner.getTitle())  // 제목 설정
                .url(imgUrl)               // 업로드된 이미지 URL 설정
                .activate(banner.getActivate() != null ? banner.getActivate() : false) // 기본값 false
                .build();

        // 저장 후 반환
        return bannerRepository.save(bannerEntity);
    }

    @Override
    @Transactional
    public Banner updateBanner(Long id, Banner updatedBanner, MultipartFile image) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 배너를 찾을 수 없습니다. id=" + id));

        if(updatedBanner.getTitle() != null)banner.setTitle(updatedBanner.getTitle());

        if(image != null ){
            s3Service.deleteFile(banner.getUrl());
            String imgUrl = s3Service.uploadImgFile(image, BucketDirectory.BANNER);
            banner.setUrl(imgUrl);

        }

        if(updatedBanner.getActivate() != null)banner.setActivate(updatedBanner.getActivate());

        return bannerRepository.save(banner);
    }

    @Override
    @Transactional
    public void deleteBanner(Long id) {
        if (!bannerRepository.existsById(id)) {
            throw new NotFoundException("해당 배너를 찾을 수 없습니다. id=" + id);
        }
        bannerRepository.deleteById(id);
    }

    public void updateBannerActivation(Long id, boolean activate) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("배너를 찾을 수 없습니다."));
        banner.setActivate(activate);
        bannerRepository.save(banner);
    }

}
