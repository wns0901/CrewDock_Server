package com.lec.spring.domains.banner.entity;

import com.lec.spring.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "banner")
public class Banner extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "boolean default false")
    private Boolean activate;

    @Column(nullable = false)
    private String url;
}
