package com.example.sirius_restapi.map.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QThumbnailEntity is a Querydsl query type for ThumbnailEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QThumbnailEntity extends EntityPathBase<ThumbnailEntity> {

    private static final long serialVersionUID = 1135387714L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QThumbnailEntity thumbnailEntity = new QThumbnailEntity("thumbnailEntity");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QLocationEntity locationEntity;

    public final StringPath thumbnailLastRegdate = createString("thumbnailLastRegdate");

    public final StringPath thumbnailPath = createString("thumbnailPath");

    public final StringPath thumbnailRegdate = createString("thumbnailRegdate");

    public QThumbnailEntity(String variable) {
        this(ThumbnailEntity.class, forVariable(variable), INITS);
    }

    public QThumbnailEntity(Path<? extends ThumbnailEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QThumbnailEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QThumbnailEntity(PathMetadata metadata, PathInits inits) {
        this(ThumbnailEntity.class, metadata, inits);
    }

    public QThumbnailEntity(Class<? extends ThumbnailEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.locationEntity = inits.isInitialized("locationEntity") ? new QLocationEntity(forProperty("locationEntity"), inits.get("locationEntity")) : null;
    }

}

