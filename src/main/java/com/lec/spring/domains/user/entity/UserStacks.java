package com.lec.spring.domains.user.entity;

import com.lec.spring.domains.stack.entity.Stack;
import com.lec.spring.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString(callSuper = true)
public class UserStacks extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Stack stack;

    @ManyToOne(optional = false)
    private User user;

}
