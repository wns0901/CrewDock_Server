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
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                .attachments(new ArrayList<>())
                .user(user)
                .project(project)
                .build();

        return new PostDTO(postRepository.save(post));
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
    @Transactional
    public PostDTO getPostDetail(Long postId) {
        return postRepository.findPostById(postId);
    }

    @Override
    public List<PostDTO> getPostsByCategory(Category category) {
        return postRepository.findByCategory(category);
    }

    @Override
    public Map<String, Object> getPosts(PostDTO postDTO, Pageable pageable) {
        Page<PostDTO> postPages = postRepository.findPosts(postDTO, pageable);
        List<Map<String, Object>> postsList = new ArrayList<>();

        for (PostDTO post : postPages.getContent()) {
            Map<String, Object> postData = new HashMap<>();
            postData.put("createdAt", post.getCreatedAt());
            postData.put("id", post.getId());
            postData.put("userId", post.getUserId());
            postData.put("projectId", post.getProjectId());
            postData.put("userNickname", post.getUserNickname());
            postData.put("category", post.getCategory());
            postData.put("direction", post.getDirection());
            postData.put("title", post.getTitle());
            postData.put("content", post.getContent());

            postsList.add(postData);
        }

        Map<String, Object> postsWrapper = new HashMap<>();
        postsWrapper.put("post", postsList);

        Map<String, Object> response = new HashMap<>();
        response.put("posts", postsWrapper);
        response.put("totalPages", postPages.getTotalPages());
        response.put("currentPage", postPages.getNumber() + 1);
        response.put("pageSize", postPages.getSize());

        return response;
    }

    @Override
    public Post getProjectPostDetail(Long postId, Long projectId) {
        Post projectPostById = postRepository.findProjectPostById(postId, projectId);
        if (projectPostById == null) return null;
        return new PostDTO(projectPostById);
    }

    @Override
    public List<PostDTO> getPostsByDirection(Long projectId, Direction direction) {
        return postRepository.findByDirection(projectId, direction);
    }

    @Override
    public List<PostDTO> getUserPostWithLimit(Long userId, int row) {
        List<Post> posts = postRepository.findByUserIdWithrowQuertDSL(userId, row);
        return posts.stream()
                .map(this::convertToPostDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> getUserPost(Long userId) {
        List<Post> posts = postRepository.findByUserIdQuertDSL(userId);
        return posts.stream()
                .map(this::convertToPostDTO)
                .collect(Collectors.toList());
    }


    // Post 엔티티 -> PostDTO 변환 메서드
    private PostDTO convertToPostDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setUserId(post.getUser().getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        postDTO.setCategory(post.getCategory());
        postDTO.setDirection(post.getDirection());
        postDTO.setAttachments(post.getAttachments());
        postDTO.setComments(post.getComments());
        if(post.getProject() != null) {
            postDTO.setProjectId(post.getProject().getId());
        }
        return postDTO;
    }
}
