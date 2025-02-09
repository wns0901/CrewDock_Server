package com.lec.spring.domains.post.entity;

import com.lec.spring.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
@Entity
public class PostAttachment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private Long postId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String url;
}
