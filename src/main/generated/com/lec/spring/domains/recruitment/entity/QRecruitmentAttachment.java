package com.lec.spring.domains.recruitment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecruitmentAttachment is a Querydsl query type for RecruitmentAttachment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecruitmentAttachment extends EntityPathBase<RecruitmentAttachment> {

    private static final long serialVersionUID = -1731205931L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecruitmentAttachment recruitmentAttachment = new QRecruitmentAttachment("recruitmentAttachment");

    public final com.lec.spring.global.common.entity.QBaseEntity _super = new com.lec.spring.global.common.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QRecruitmentPost post;

    public final StringPath url = createString("url");

    public QRecruitmentAttachment(String variable) {
        this(RecruitmentAttachment.class, forVariable(variable), INITS);
    }

    public QRecruitmentAttachment(Path<? extends RecruitmentAttachment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecruitmentAttachment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecruitmentAttachment(PathMetadata metadata, PathInits inits) {
        this(RecruitmentAttachment.class, metadata, inits);
    }

    public QRecruitmentAttachment(Class<? extends RecruitmentAttachment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new QRecruitmentPost(forProperty("post"), inits.get("post")) : null;
    }

}

