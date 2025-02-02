package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.dto.ProjectPostDTO;
import com.lec.spring.domains.post.entity.Direction;
import com.lec.spring.domains.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectPostService extends BasePostService {
    Post getProjectPostDetail(Long postId, Long projectId);

    List<ProjectPostDTO> getPostsByDirection(Long projectId, Direction direction);

    Page<ProjectPostDTO> getPostsByDirectionPage(Long projectId, Direction direction, Pageable pageable);
}
