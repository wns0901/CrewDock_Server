package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.entity.Direction;
import com.lec.spring.domains.post.entity.Post;

import java.util.List;

public class ProjectPostServiceImpl extends BasePostServiceImpl implements ProjectPostService {
    @Override
    public Post getPostDetailByDirection(Direction direction, Long postId, Long projectId) {
        return null;
    }

    @Override
    public List<Post> getPostsByDirection(Direction direction, Long projectId) {
        return List.of();
    }
}
