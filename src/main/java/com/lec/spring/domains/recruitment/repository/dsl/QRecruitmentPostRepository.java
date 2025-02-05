package com.lec.spring.domains.recruitment.repository.dsl;

import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import java.util.Optional;
import java.util.List;

public interface QRecruitmentPostRepository {
    Optional<RecruitmentPost> findByIdWithUserAndProject(Long id);
    List<RecruitmentPost> findByFilters(List<String> stacks, List<String> recruitedFields, List<String> regions);
}
