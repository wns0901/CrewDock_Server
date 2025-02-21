package com.lec.spring.domains.recruitment.repository.dsl;

import com.lec.spring.domains.recruitment.dto.ScrappedPostDTO;
import com.lec.spring.domains.recruitment.entity.RecruitmentScrap;
import com.lec.spring.domains.user.entity.User;

import java.util.List;

public interface QRecruitmentScrapRepository {

    List<ScrappedPostDTO> findScrappedPostsWithCommentCount(Long userId, int row);
}
