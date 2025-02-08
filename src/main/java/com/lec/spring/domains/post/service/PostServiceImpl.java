package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.dto.PostDTO;
import com.lec.spring.domains.post.entity.Category;
import com.lec.spring.domains.post.entity.Direction;
import com.lec.spring.domains.post.entity.Post;
import com.lec.spring.domains.post.repository.PostCommentRepository;
import com.lec.spring.domains.post.repository.PostRepository;
import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.repository.ProjectRepository;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final PostCommentRepository postCommentRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, ProjectRepository projectRepository, PostCommentRepository postCommentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.postCommentRepository = postCommentRepository;
    }

    @Override
    public Post savePost(PostDTO postDTO) {
        User user = userRepository.findById(postDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        Project project = null;
        if (postDTO.getProjectId() != null) {
            project = projectRepository.findById(postDTO.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project Not Found"));
        }

        Post post = Post.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .category(postDTO.getCategory())
                .direction(postDTO.getDirection())
                .user(user)
                .project(project)
                .build();

        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        return postRepository.updatePost(post);
    }

    @Transactional
    @Override
    public void deletePost(Long postId) {
        postCommentRepository.deleteAllCommentsByPostId(postId);
        postRepository.deletePostById(postId);
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
    public Page<PostDTO> getPosts(PostDTO postDTO, Pageable pageable) {
        return postRepository.findPosts(postDTO, pageable);
    }

    @Override
    public Post getProjectPostDetail(Long postId, Long projectId) {
        return postRepository.findProjectPostById(postId, projectId);
    }

    @Override
    public List<PostDTO> getPostsByDirection(Long projectId, Direction direction) {
        return postRepository.findByDirection(projectId, direction);
    }
}
