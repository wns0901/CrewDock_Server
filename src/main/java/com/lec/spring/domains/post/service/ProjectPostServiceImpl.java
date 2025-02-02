package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.dto.ProjectPostDTO;
import com.lec.spring.domains.post.entity.Direction;
import com.lec.spring.domains.post.entity.Post;
import com.lec.spring.domains.post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectPostServiceImpl extends BasePostServiceImpl implements ProjectPostService {
    private final PostRepository postRepository;

    public ProjectPostServiceImpl(PostRepository postRepository, PostRepository postRepository1) {
        super(postRepository);
        this.postRepository = postRepository1;
    }

    @Override
    public Post getProjectPostDetail(Long postId, Long projectId) {
        return postRepository.findProjectPostById(postId, projectId);
    }

    @Override
    public List<ProjectPostDTO> getPostsByDirection(Long projectId, Direction direction) {
        return postRepository.findByDirection(projectId, direction);
    }

    @Override
    public Page<ProjectPostDTO> getPostsByDirectionPage(Long projectId, Direction direction, Pageable pageable) {
        return postRepository.findByDirectionPage(projectId, direction, pageable);
    }
}
