package com.lec.spring.domains.stack.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStack is a Querydsl query type for Stack
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStack extends EntityPathBase<Stack> {

    private static final long serialVersionUID = 349558098L;

    public static final QStack stack = new QStack("stack");

    public final com.lec.spring.global.common.entity.QBaseEntity _super = new com.lec.spring.global.common.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QStack(String variable) {
        super(Stack.class, forVariable(variable));
    }

    public QStack(Path<? extends Stack> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStack(PathMetadata metadata) {
        super(Stack.class, metadata);
    }

}

