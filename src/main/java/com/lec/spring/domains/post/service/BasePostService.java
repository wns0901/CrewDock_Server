package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.entity.Post;

public interface BasePostService {
    Post getPostById(Long postId);

    Post savePost(Post post);

    Post updatePost(Post post);

    void deletePost(Long postId);
}
