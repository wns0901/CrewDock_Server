package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.entity.Direction;
import com.lec.spring.domains.post.entity.Post;

import java.util.List;

public interface ProjectPostService extends BasePostService {
    Post getPostDetailByDirection(Direction direction, Long postId, Long projectId);

    List<Post> getPostsByDirection(Direction direction, Long projectId);
}
