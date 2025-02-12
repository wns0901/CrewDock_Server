package com.lec.spring.domains.user.entity;

import com.lec.spring.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "user_auth")
public class UserAuth extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    @JsonIgnore
    @ToString.Exclude
    private Long userId;

    @ManyToOne
    private Auth auth;

}
