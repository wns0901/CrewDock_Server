package com.lec.spring.domains.recruitment.repository.dsl;

import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.recruitment.entity.ProceedMethod;
import com.lec.spring.domains.recruitment.entity.RecruitmentPost;
import com.lec.spring.domains.recruitment.entity.Region;
import com.lec.spring.domains.stack.entity.Stack;
import com.lec.spring.domains.user.entity.User;

import java.util.List;

public interface QRecruitmentPostRepository {
    // https://ittrue.tistory.com/292
    // https://chaeyami.tistory.com/258
    // https://www.baeldung.com/querydsl-with-jpa-tutorial

    //문자열 타입을 어떻게 필터화 시키지...
// recruitedField
    // 문자열들... 슬라이스 해서 , 빼고 확인해봐야함
    List<RecruitmentPost> filterPosts(List<Stack> stacks, /*list<RecruitedField> recruitedFields,*/ List<ProceedMethod> proceedMethods, List<Region> regions);

    // 내가 만든 것들만 필터링 해오기
//    List<RecruitmentPost> myPosts(User user, Project project);
}
