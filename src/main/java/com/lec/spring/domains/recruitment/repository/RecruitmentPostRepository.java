package com.lec.spring.domains.recruitment.repository;

import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.repository.dsl.QRecruitmentPostRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public interface RecruitmentPostRepository extends JpaRepository<RecruitmentPost, Long>, QRecruitmentPostRepository {

    @Query("SELECT u FROM RecruitmentPost u WHERE u.user.id = :userId")
    List<RecruitmentPost> findAllByUserId(@Param("userId") Long userId);

    // 집가서 처음부터 생각해야할지도~
}
