package com.lec.spring.domains.project.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProjectIssue is a Querydsl query type for ProjectIssue
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProjectIssue extends EntityPathBase<ProjectIssue> {

    private static final long serialVersionUID = 2121916679L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProjectIssue projectIssue = new QProjectIssue("projectIssue");

    public final com.lec.spring.global.common.entity.QBaseEntity _super = new com.lec.spring.global.common.entity.QBaseEntity(this);

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DatePath<java.time.LocalDate> deadline = createDate("deadline", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath issueName = createString("issueName");

    public final com.lec.spring.domains.user.entity.QUser manager;

    public final EnumPath<ProjectIssuePriority> priority = createEnum("priority", ProjectIssuePriority.class);

    public final QProject project;

    public final DatePath<java.time.LocalDate> startline = createDate("startline", java.time.LocalDate.class);

    public final EnumPath<ProjectIssueStatus> status = createEnum("status", ProjectIssueStatus.class);

    public final com.lec.spring.domains.user.entity.QUser writer;

    public QProjectIssue(String variable) {
        this(ProjectIssue.class, forVariable(variable), INITS);
    }

    public QProjectIssue(Path<? extends ProjectIssue> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProjectIssue(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProjectIssue(PathMetadata metadata, PathInits inits) {
        this(ProjectIssue.class, metadata, inits);
    }

    public QProjectIssue(Class<? extends ProjectIssue> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.manager = inits.isInitialized("manager") ? new com.lec.spring.domains.user.entity.QUser(forProperty("manager")) : null;
        this.project = inits.isInitialized("project") ? new QProject(forProperty("project")) : null;
        this.writer = inits.isInitialized("writer") ? new com.lec.spring.domains.user.entity.QUser(forProperty("writer")) : null;
    }

}

