package com.lec.spring.domains.recruitment.repository.dsl;

import com.lec.spring.domains.recruitment.entity.ProceedMethod;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.entity.Region;
import com.lec.spring.domains.stack.entity.Stack;
import com.lec.spring.global.common.entity.Position;

import java.util.List;

public interface QRecruitmentPostRepository {
    // https://ittrue.tistory.com/292
    // https://chaeyami.tistory.com/258
    // https://www.baeldung.com/querydsl-with-jpa-tutorial

    List<RecruitmentPost> filterPosts(List<Stack> stacks, List<Position> positions, List<ProceedMethod> proceedMethods, List<Region> regions);

}
