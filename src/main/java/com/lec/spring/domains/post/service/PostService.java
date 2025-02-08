package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.dto.PostDTO;
import com.lec.spring.domains.post.entity.Category;
import com.lec.spring.domains.post.entity.Direction;
import com.lec.spring.domains.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService{
    Post savePost(PostDTO postDTO);

    Post updatePost(Post post);

    void deletePost(Long postId);

    Post getPostDetail(Long postId);

    List<PostDTO> getPostsByCategory(Category category);

<<<<<<< HEAD
    Page<PostDTO> getPostsByCategoryPage(Category category, Pageable pageable);

    List<PostDTO> getUserPostWithLimit(Long userId, int row);

    List<PostDTO> getUserPost(Long userId);
=======
    Page<PostDTO> getPosts(PostDTO postDTO, Pageable pageable);

    Post getProjectPostDetail(Long postId, Long projectId);

    List<PostDTO> getPostsByDirection(Long projectId, Direction direction);
>>>>>>> bba51ea (Feat(#6): postController 수정)
}
