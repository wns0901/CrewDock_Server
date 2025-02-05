package com.lec.spring.domains.recruitment.repository.dsl;

import com.lec.spring.domains.recruitment.entity.RecruitmentComment;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;

import java.util.List;

public interface QRecruitmentCommentRepository {

    List<RecruitmentComment> commentListByRecruitmentPost(RecruitmentPost recruitmentPost);
}
