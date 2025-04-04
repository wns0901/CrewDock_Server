package com.lec.spring.domains.post.repository.dsl;

import com.lec.spring.domains.post.entity.Post;
import com.lec.spring.domains.post.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QPostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    void findById() {

        Post post = postRepository.findById(1L).get();

        System.out.println(post);
    }
}