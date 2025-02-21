package com.lec.spring.domains.post.repository;

import com.lec.spring.domains.post.entity.Post;
import com.lec.spring.domains.post.repository.dsl.QPostRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, QPostRepository {
}
