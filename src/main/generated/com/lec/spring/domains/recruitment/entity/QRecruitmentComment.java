package com.lec.spring.domains.recruitment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecruitmentComment is a Querydsl query type for RecruitmentComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecruitmentComment extends EntityPathBase<RecruitmentComment> {

    private static final long serialVersionUID = -953790483L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecruitmentComment recruitmentComment = new QRecruitmentComment("recruitmentComment");

    public final com.lec.spring.global.common.entity.QBaseEntity _super = new com.lec.spring.global.common.entity.QBaseEntity(this);

    public final QRecruitmentComment comment;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath deleted = createBoolean("deleted");

    public final BooleanPath fixed = createBoolean("fixed");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QRecruitmentPost post;

    public final com.lec.spring.domains.user.entity.QUser user;

    public QRecruitmentComment(String variable) {
        this(RecruitmentComment.class, forVariable(variable), INITS);
    }

    public QRecruitmentComment(Path<? extends RecruitmentComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecruitmentComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecruitmentComment(PathMetadata metadata, PathInits inits) {
        this(RecruitmentComment.class, metadata, inits);
    }

    public QRecruitmentComment(Class<? extends RecruitmentComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.comment = inits.isInitialized("comment") ? new QRecruitmentComment(forProperty("comment"), inits.get("comment")) : null;
        this.post = inits.isInitialized("post") ? new QRecruitmentPost(forProperty("post"), inits.get("post")) : null;
        this.user = inits.isInitialized("user") ? new com.lec.spring.domains.user.entity.QUser(forProperty("user")) : null;
    }

}

