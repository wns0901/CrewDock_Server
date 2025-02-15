package com.lec.spring.domains.recruitment.repository;

import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.entity.RecruitmentScrap;
import com.lec.spring.domains.recruitment.repository.dsl.QRecruitmentScrapRepository;
import com.lec.spring.domains.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruitmentScrapRepository extends JpaRepository<RecruitmentScrap, Long>, QRecruitmentScrapRepository {

    boolean existsByUserAndRecruitment(User user, RecruitmentPost post);

    void deleteByUserAndRecruitment(User user, RecruitmentPost recruitment);

    List<RecruitmentScrap> findByUser(User user);
}


