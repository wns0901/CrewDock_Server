package com.lec.spring.domains.post.dto;

import com.lec.spring.domains.post.entity.Post;
import lombok.*;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class PostDTO extends Post {
    private Long userId;
    private Long projectId;

    private List<Map<String, Object>> filteredComments;
}
