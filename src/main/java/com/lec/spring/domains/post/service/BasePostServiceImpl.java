package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.entity.Post;
import com.lec.spring.domains.post.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class BasePostServiceImpl implements BasePostService {
    private final PostRepository postRepository;

    public BasePostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post getPostById(Long postId) {
        return postRepository.findByPostId(postId);
    }

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
