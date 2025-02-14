package com.lec.spring.domains.project.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QResignationLetter is a Querydsl query type for ResignationLetter
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QResignationLetter extends EntityPathBase<ResignationLetter> {

    private static final long serialVersionUID = -2120427612L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QResignationLetter resignationLetter = new QResignationLetter("resignationLetter");

    public final com.lec.spring.global.common.entity.QBaseEntity _super = new com.lec.spring.global.common.entity.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QProjectMember member;

    public QResignationLetter(String variable) {
        this(ResignationLetter.class, forVariable(variable), INITS);
    }

    public QResignationLetter(Path<? extends ResignationLetter> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QResignationLetter(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QResignationLetter(PathMetadata metadata, PathInits inits) {
        this(ResignationLetter.class, metadata, inits);
    }

    public QResignationLetter(Class<? extends ResignationLetter> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QProjectMember(forProperty("member"), inits.get("member")) : null;
    }

}

