package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.entity.Post;
import com.lec.spring.domains.post.entity.PostAttachment;
import com.lec.spring.domains.post.entity.PostComment;

import java.util.List;

public class BasePostServiceImpl implements BasePostService {
    @Override
    public Post getPostById(Long postId) {
        return null;
    }

    @Override
    public Post savePost(Post post) {
        return null;
    }

    @Override
    public Post updatePost(Post post) {
        return null;
    }

    @Override
    public int deletePost(Long postId) {
        return 0;
    }

    @Override
    public List<PostAttachment> findByPostId(Long postId) {
        return List.of();
    }

    @Override
    public List<PostComment> getCommentsByPostId(Long postId) {
        return List.of();
    }
}
