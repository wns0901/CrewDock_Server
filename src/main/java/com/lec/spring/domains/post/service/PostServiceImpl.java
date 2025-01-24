package com.lec.spring.domains.post.service;

import com.lec.spring.domains.post.entity.Category;
import com.lec.spring.domains.post.entity.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl extends BasePostServiceImpl implements PostService {

    public PostServiceImpl(JPAQueryFactory queryFactory) {
        super(queryFactory);
    }

    @Override
    public Post getPostDetailByCategory(Category category, Long postId) {
        return null;
    }

    @Override
    public List<Post> getPostsByCategory(Category category) {
        return List.of();
    }
}
