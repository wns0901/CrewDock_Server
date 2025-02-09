package com.lec.spring.domains.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuth is a Querydsl query type for Auth
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuth extends EntityPathBase<Auth> {

    private static final long serialVersionUID = 414810655L;

    public static final QAuth auth = new QAuth("auth");

    public final com.lec.spring.global.common.entity.QBaseEntity _super = new com.lec.spring.global.common.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QAuth(String variable) {
        super(Auth.class, forVariable(variable));
    }

    public QAuth(Path<? extends Auth> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuth(PathMetadata metadata) {
        super(Auth.class, metadata);
    }

}

