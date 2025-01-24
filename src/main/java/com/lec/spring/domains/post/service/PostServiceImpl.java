package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.entity.Category;
import com.lec.spring.domains.post.entity.Post;

import java.util.List;

public class PostServiceImpl extends BasePostServiceImpl implements PostService {
    @Override
    public Post getPostDetailByCategory(Category category, Long postId) {
        return null;
    }

    @Override
    public List<Post> getPostsByCategory(Category category) {
        return List.of();
    }
}
