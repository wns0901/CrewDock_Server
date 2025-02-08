package com.lec.spring.domains.post.repository.dsl;

import com.lec.spring.domains.post.dto.PostDTO;
import com.lec.spring.domains.post.dto.ProjectPostDTO;
import com.lec.spring.domains.post.entity.Category;
import com.lec.spring.domains.post.entity.Direction;
import com.lec.spring.domains.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface QPostRepository {
    Post findByPostId(Long postId);

    List<PostDTO> findByCategory(Category category);

    Post findPostById(Long postId);

    Page<PostDTO> findByCategoryPage(Category category, Pageable pageable);

    List<ProjectPostDTO> findByDirection(Long projectId, Direction direction);

    Post findProjectPostById(Long postId, Long projectId);

    Page<ProjectPostDTO> findByDirectionPage(Long projectId, Direction direction, Pageable pageable);

    List<Post> findByUserIdWithrowQuertDSL(Long userId, int row);

    List<Post> findByUserIdQuertDSL(Long userId);
}
