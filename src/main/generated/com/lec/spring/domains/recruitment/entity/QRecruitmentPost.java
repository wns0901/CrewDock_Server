package com.lec.spring.domains.recruitment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecruitmentPost is a Querydsl query type for RecruitmentPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecruitmentPost extends EntityPathBase<RecruitmentPost> {

    private static final long serialVersionUID = 1334071762L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecruitmentPost recruitmentPost = new QRecruitmentPost("recruitmentPost");

    public final com.lec.spring.global.common.entity.QBaseEntity _super = new com.lec.spring.global.common.entity.QBaseEntity(this);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DatePath<java.time.LocalDate> deadline = createDate("deadline", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<ProceedMethod> proceedMethod = createEnum("proceedMethod", ProceedMethod.class);

    public final com.lec.spring.domains.project.entity.QProject project;

    public final StringPath recruitedField = createString("recruitedField");

    public final NumberPath<Integer> recruitedNumber = createNumber("recruitedNumber", Integer.class);

    public final EnumPath<Region> region = createEnum("region", Region.class);

    public final StringPath title = createString("title");

    public final com.lec.spring.domains.user.entity.QUser user;

    public QRecruitmentPost(String variable) {
        this(RecruitmentPost.class, forVariable(variable), INITS);
    }

    public QRecruitmentPost(Path<? extends RecruitmentPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecruitmentPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecruitmentPost(PathMetadata metadata, PathInits inits) {
        this(RecruitmentPost.class, metadata, inits);
    }

    public QRecruitmentPost(Class<? extends RecruitmentPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.project = inits.isInitialized("project") ? new com.lec.spring.domains.project.entity.QProject(forProperty("project")) : null;
        this.user = inits.isInitialized("user") ? new com.lec.spring.domains.user.entity.QUser(forProperty("user")) : null;
    }

}

