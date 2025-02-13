package com.lec.spring.domains.portfolio.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPortfolioStack is a Querydsl query type for PortfolioStack
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPortfolioStack extends EntityPathBase<PortfolioStack> {

    private static final long serialVersionUID = 2138156246L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPortfolioStack portfolioStack = new QPortfolioStack("portfolioStack");

    public final com.lec.spring.global.common.entity.QBaseEntity _super = new com.lec.spring.global.common.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> portfolio = createNumber("portfolio", Long.class);

    public final com.lec.spring.domains.stack.entity.QStack stack;

    public QPortfolioStack(String variable) {
        this(PortfolioStack.class, forVariable(variable), INITS);
    }

    public QPortfolioStack(Path<? extends PortfolioStack> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPortfolioStack(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPortfolioStack(PathMetadata metadata, PathInits inits) {
        this(PortfolioStack.class, metadata, inits);
    }

    public QPortfolioStack(Class<? extends PortfolioStack> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.stack = inits.isInitialized("stack") ? new com.lec.spring.domains.stack.entity.QStack(forProperty("stack")) : null;
    }

}

