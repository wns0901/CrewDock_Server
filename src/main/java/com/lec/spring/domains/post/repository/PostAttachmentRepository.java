package com.lec.spring.domains.post.repository;

import com.lec.spring.domains.post.entity.PostAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostAttachmentRepository extends JpaRepository<PostAttachment, Long> {
}
