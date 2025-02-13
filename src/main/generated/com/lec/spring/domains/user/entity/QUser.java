package com.lec.spring.domains.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 415404098L;

    public static final QUser user = new QUser("user");

    public final com.lec.spring.global.common.entity.QBaseEntity _super = new com.lec.spring.global.common.entity.QBaseEntity(this);

    public final StringPath blogUrl = createString("blogUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath githubUrl = createString("githubUrl");

    public final EnumPath<com.lec.spring.global.common.entity.Position> hopePosition = createEnum("hopePosition", com.lec.spring.global.common.entity.Position.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath notionUrl = createString("notionUrl");

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath profileImgUrl = createString("profileImgUrl");

    public final ListPath<com.lec.spring.domains.project.entity.ProjectMember, com.lec.spring.domains.project.entity.QProjectMember> projectMembers = this.<com.lec.spring.domains.project.entity.ProjectMember, com.lec.spring.domains.project.entity.QProjectMember>createList("projectMembers", com.lec.spring.domains.project.entity.ProjectMember.class, com.lec.spring.domains.project.entity.QProjectMember.class, PathInits.DIRECT2);

    public final StringPath provider = createString("provider");

    public final StringPath providerId = createString("providerId");

    public final StringPath selfIntroduction = createString("selfIntroduction");

    public final ListPath<UserAuth, QUserAuth> userAuths = this.<UserAuth, QUserAuth>createList("userAuths", UserAuth.class, QUserAuth.class, PathInits.DIRECT2);

    public final StringPath username = createString("username");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

