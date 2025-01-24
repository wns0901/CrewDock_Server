package com.lec.spring.domains.portfolio.entity;

import com.lec.spring.domains.stack.entity.Stack;
import com.lec.spring.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = true)
@Entity(name = "portfolio_stack")
public class PortfolioStack extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Stack stack;

    @Column(name = "portfolio_id")
    @JsonIgnore
    @ToString.Exclude
    private Long portfolio;
}
