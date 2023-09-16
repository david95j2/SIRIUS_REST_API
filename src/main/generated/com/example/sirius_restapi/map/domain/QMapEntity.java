package com.example.sirius_restapi.map.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMapEntity is a Querydsl query type for MapEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMapEntity extends EntityPathBase<MapEntity> {

    private static final long serialVersionUID = 1049976562L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMapEntity mapEntity = new QMapEntity("mapEntity");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QLocationEntity locationEntity;

    public final NumberPath<Integer> mapArea = createNumber("mapArea", Integer.class);

    public final NumberPath<Integer> mapCount = createNumber("mapCount", Integer.class);

    public final StringPath mapPath = createString("mapPath");

    public final StringPath regdate = createString("regdate");

    public QMapEntity(String variable) {
        this(MapEntity.class, forVariable(variable), INITS);
    }

    public QMapEntity(Path<? extends MapEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMapEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMapEntity(PathMetadata metadata, PathInits inits) {
        this(MapEntity.class, metadata, inits);
    }

    public QMapEntity(Class<? extends MapEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.locationEntity = inits.isInitialized("locationEntity") ? new QLocationEntity(forProperty("locationEntity"), inits.get("locationEntity")) : null;
    }

}

