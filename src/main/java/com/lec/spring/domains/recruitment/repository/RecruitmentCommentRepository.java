package com.lec.spring.domains.recruitment.repository;

import com.lec.spring.domains.recruitment.entity.RecruitmentComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecruitmentCommentRepository extends JpaRepository<RecruitmentComment, Long> {

//     // 특정 댓글의 부모 댓글의 확인
//    @Query(value = """
//                select b from RecruitmentComment""")
//    List<RecruitmentComment> findByParentsId(@Param("parents_id")RecruitmentComment comment);
}
