package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.dto.PostDTO;
import com.lec.spring.domains.post.entity.Category;
import com.lec.spring.domains.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService extends BasePostService {
    Post getPostDetail(Long postId);

    List<PostDTO> getPostsByCategory(Category category);

    Page<PostDTO> getPostsByCategoryPage(Category category, Pageable pageable);
}
