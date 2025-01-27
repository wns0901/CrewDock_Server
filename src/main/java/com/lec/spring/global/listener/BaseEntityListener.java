package com.lec.spring.global.listener;

import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

// @EntityListeners 로 Entity 에 지정이 되면
// Entity 가 아닌객체임에도
// @PrePersist, @PreUpdate 지정가능
public class BaseEntityListener {
    @PrePersist
    void prePersist(Object o) { // 반드시 Object 매개변수 필요 (해당 event 가 발행한 entity)
        if (o instanceof Auditable) {
            ((Auditable) o).setCreatedAt(LocalDateTime.now());
        }
    }
}
