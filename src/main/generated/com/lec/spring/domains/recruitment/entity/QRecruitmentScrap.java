package com.lec.spring.domains.recruitment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecruitmentScrap is a Querydsl query type for RecruitmentScrap
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecruitmentScrap extends EntityPathBase<RecruitmentScrap> {

    private static final long serialVersionUID = -1591036705L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecruitmentScrap recruitmentScrap = new QRecruitmentScrap("recruitmentScrap");

    public final com.lec.spring.global.common.entity.QBaseEntity _super = new com.lec.spring.global.common.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QRecruitmentPost recruitment;

    public final com.lec.spring.domains.user.entity.QUser user;

    public QRecruitmentScrap(String variable) {
        this(RecruitmentScrap.class, forVariable(variable), INITS);
    }

    public QRecruitmentScrap(Path<? extends RecruitmentScrap> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecruitmentScrap(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecruitmentScrap(PathMetadata metadata, PathInits inits) {
        this(RecruitmentScrap.class, metadata, inits);
    }

    public QRecruitmentScrap(Class<? extends RecruitmentScrap> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.recruitment = inits.isInitialized("recruitment") ? new QRecruitmentPost(forProperty("recruitment"), inits.get("recruitment")) : null;
        this.user = inits.isInitialized("user") ? new com.lec.spring.domains.user.entity.QUser(forProperty("user")) : null;
    }

}

