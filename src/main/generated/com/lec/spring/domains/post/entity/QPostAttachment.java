package com.lec.spring.domains.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPostAttachment is a Querydsl query type for PostAttachment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostAttachment extends EntityPathBase<PostAttachment> {

    private static final long serialVersionUID = 1277587631L;

    public static final QPostAttachment postAttachment = new QPostAttachment("postAttachment");

    public final com.lec.spring.global.common.entity.QBaseEntity _super = new com.lec.spring.global.common.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> postId = createNumber("postId", Long.class);

    public final StringPath url = createString("url");

    public QPostAttachment(String variable) {
        super(PostAttachment.class, forVariable(variable));
    }

    public QPostAttachment(Path<? extends PostAttachment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPostAttachment(PathMetadata metadata) {
        super(PostAttachment.class, metadata);
    }

}

