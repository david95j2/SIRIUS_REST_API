package com.example.sirius_restapi.mission.mapping.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGlobalWayPointEntity is a Querydsl query type for GlobalWayPointEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGlobalWayPointEntity extends EntityPathBase<GlobalWayPointEntity> {

    private static final long serialVersionUID = 1863662419L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGlobalWayPointEntity globalWayPointEntity = new QGlobalWayPointEntity("globalWayPointEntity");

    public final NumberPath<Double> altitude = createNumber("altitude", Double.class);

    public final QGlobalMissionEntity globalMissionEntity;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final StringPath wait = createString("wait");

    public QGlobalWayPointEntity(String variable) {
        this(GlobalWayPointEntity.class, forVariable(variable), INITS);
    }

    public QGlobalWayPointEntity(Path<? extends GlobalWayPointEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGlobalWayPointEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGlobalWayPointEntity(PathMetadata metadata, PathInits inits) {
        this(GlobalWayPointEntity.class, metadata, inits);
    }

    public QGlobalWayPointEntity(Class<? extends GlobalWayPointEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.globalMissionEntity = inits.isInitialized("globalMissionEntity") ? new QGlobalMissionEntity(forProperty("globalMissionEntity"), inits.get("globalMissionEntity")) : null;
    }

}

