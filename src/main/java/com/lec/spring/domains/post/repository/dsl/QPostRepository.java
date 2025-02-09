package com.lec.spring.domains.post.repository.dsl;

import com.lec.spring.domains.post.dto.PostDTO;
import com.lec.spring.domains.post.entity.Category;
import com.lec.spring.domains.post.entity.Direction;
import com.lec.spring.domains.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QPostRepository {
    Post findByPostId(Long postId);

    Post updatePost(Post post);

    List<PostDTO> findByCategory(Category category);

    PostDTO findPostById(Long postId);

    Page<PostDTO> findPosts(PostDTO postDTO, Pageable pageable);

    List<PostDTO> findByDirection(Long projectId, Direction direction);

    Post findProjectPostById(Long postId, Long projectId);

    void deletePostById(Long postId);

    List<Post> findByUserIdWithrowQuertDSL(Long userId, int row);

    List<Post> findByUserIdQuertDSL(Long userId);
}
