package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.dto.PostDTO;
import com.lec.spring.domains.post.entity.Category;
import com.lec.spring.domains.post.entity.Direction;
import com.lec.spring.domains.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface PostService{
    Post savePost(PostDTO postDTO);

    Post updatePost(PostDTO postDTO);

    void deletePost(Long postId);

    PostDTO getPostDetail(Long postId);

    List<PostDTO> getPostsByCategory(Category category);

    Map<String, Object> getPosts(PostDTO postDTO, Pageable pageable);

    Post getProjectPostDetail(Long postId, Long projectId);

    List<PostDTO> getPostsByDirection(Long projectId, Direction direction);

    List<PostDTO> getUserPostWithLimit(Long userId, int row);

    List<PostDTO> getUserPost(Long userId);
}
