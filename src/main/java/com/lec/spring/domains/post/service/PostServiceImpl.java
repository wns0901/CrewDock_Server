package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.dto.PostDTO;
import com.lec.spring.domains.post.entity.Category;
import com.lec.spring.domains.post.entity.Post;
import com.lec.spring.domains.post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl extends BasePostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository, PostRepository postRepository1) {
        super(postRepository);
        this.postRepository = postRepository1;
    }

    @Override
    public Post getPostDetail(Long postId) {
        return postRepository.findPostById(postId);
    }

    @Override
    public List<PostDTO> getPostsByCategory(Category category) {
        return postRepository.findByCategory(category);
    }

    @Override
    public Page<PostDTO> getPostsByCategoryPage(Category category, Pageable pageable) {
        return postRepository.findByCategoryPage(category, pageable);
    }
}
