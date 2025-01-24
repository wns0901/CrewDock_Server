package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.entity.Category;
import com.lec.spring.domains.post.entity.Post;

import java.util.List;

public interface PostService {
    Post getPostDetailByCategory(Category category, Long postId);

    List<Post> getPostsByCategory(Category category);
}
