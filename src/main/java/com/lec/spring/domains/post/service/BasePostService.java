package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.entity.Post;
import com.lec.spring.domains.post.entity.PostAttachment;
import com.lec.spring.domains.post.entity.PostComment;

import java.util.List;

public interface BasePostService {
    Post getPostById(Long postId);

    Post savePost(Post post);

    Post updatePost(Post post);

    int deletePost(Long postId);
}
