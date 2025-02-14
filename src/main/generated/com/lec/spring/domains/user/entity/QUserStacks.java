package com.lec.spring.domains.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserStacks is a Querydsl query type for UserStacks
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserStacks extends EntityPathBase<UserStacks> {

    private static final long serialVersionUID = -1205579315L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserStacks userStacks = new QUserStacks("userStacks");

    public final com.lec.spring.global.common.entity.QBaseEntity _super = new com.lec.spring.global.common.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.lec.spring.domains.stack.entity.QStack stack;

    public final QUser user;

    public QUserStacks(String variable) {
        this(UserStacks.class, forVariable(variable), INITS);
    }

    public QUserStacks(Path<? extends UserStacks> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserStacks(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserStacks(PathMetadata metadata, PathInits inits) {
        this(UserStacks.class, metadata, inits);
    }

    public QUserStacks(Class<? extends UserStacks> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.stack = inits.isInitialized("stack") ? new com.lec.spring.domains.stack.entity.QStack(forProperty("stack")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

