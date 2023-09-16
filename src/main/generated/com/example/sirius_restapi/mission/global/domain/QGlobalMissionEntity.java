package com.example.sirius_restapi.mission.global.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGlobalMissionEntity is a Querydsl query type for GlobalMissionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGlobalMissionEntity extends EntityPathBase<GlobalMissionEntity> {

    private static final long serialVersionUID = 1462056576L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGlobalMissionEntity globalMissionEntity = new QGlobalMissionEntity("globalMissionEntity");

    public final ListPath<GlobalWayPointEntity, QGlobalWayPointEntity> globalWayPointEntities = this.<GlobalWayPointEntity, QGlobalWayPointEntity>createList("globalWayPointEntities", GlobalWayPointEntity.class, QGlobalWayPointEntity.class, PathInits.DIRECT2);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath missionName = createString("missionName");

    public final StringPath missionType = createString("missionType");

    public final com.example.sirius_restapi.user.domain.QUserEntity userEntity;

    public QGlobalMissionEntity(String variable) {
        this(GlobalMissionEntity.class, forVariable(variable), INITS);
    }

    public QGlobalMissionEntity(Path<? extends GlobalMissionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGlobalMissionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGlobalMissionEntity(PathMetadata metadata, PathInits inits) {
        this(GlobalMissionEntity.class, metadata, inits);
    }

    public QGlobalMissionEntity(Class<? extends GlobalMissionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userEntity = inits.isInitialized("userEntity") ? new com.example.sirius_restapi.user.domain.QUserEntity(forProperty("userEntity")) : null;
    }

}

