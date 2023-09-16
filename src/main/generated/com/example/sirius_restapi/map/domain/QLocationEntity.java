package com.example.sirius_restapi.map.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLocationEntity is a Querydsl query type for LocationEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLocationEntity extends EntityPathBase<LocationEntity> {

    private static final long serialVersionUID = -477701819L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLocationEntity locationEntity = new QLocationEntity("locationEntity");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Float> latitude = createNumber("latitude", Float.class);

    public final StringPath location = createString("location");

    public final NumberPath<Float> longitude = createNumber("longitude", Float.class);

    public final ListPath<ThumbnailEntity, QThumbnailEntity> thumbnailEntities = this.<ThumbnailEntity, QThumbnailEntity>createList("thumbnailEntities", ThumbnailEntity.class, QThumbnailEntity.class, PathInits.DIRECT2);

    public final com.example.sirius_restapi.user.domain.QUserEntity userEntity;

    public QLocationEntity(String variable) {
        this(LocationEntity.class, forVariable(variable), INITS);
    }

    public QLocationEntity(Path<? extends LocationEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLocationEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLocationEntity(PathMetadata metadata, PathInits inits) {
        this(LocationEntity.class, metadata, inits);
    }

    public QLocationEntity(Class<? extends LocationEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userEntity = inits.isInitialized("userEntity") ? new com.example.sirius_restapi.user.domain.QUserEntity(forProperty("userEntity")) : null;
    }

}

