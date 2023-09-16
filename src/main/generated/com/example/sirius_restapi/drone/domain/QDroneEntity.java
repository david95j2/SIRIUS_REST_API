package com.example.sirius_restapi.drone.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDroneEntity is a Querydsl query type for DroneEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDroneEntity extends EntityPathBase<DroneEntity> {

    private static final long serialVersionUID = -1683214094L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDroneEntity droneEntity = new QDroneEntity("droneEntity");

    public final StringPath droneType = createString("droneType");

    public final NumberPath<Float> droneVoltageMax = createNumber("droneVoltageMax", Float.class);

    public final NumberPath<Float> droneVoltageMin = createNumber("droneVoltageMin", Float.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final com.example.sirius_restapi.user.domain.QUserEntity userEntity;

    public final NumberPath<Integer> xDimension = createNumber("xDimension", Integer.class);

    public final NumberPath<Integer> yDimension = createNumber("yDimension", Integer.class);

    public final NumberPath<Integer> zDimension = createNumber("zDimension", Integer.class);

    public QDroneEntity(String variable) {
        this(DroneEntity.class, forVariable(variable), INITS);
    }

    public QDroneEntity(Path<? extends DroneEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDroneEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDroneEntity(PathMetadata metadata, PathInits inits) {
        this(DroneEntity.class, metadata, inits);
    }

    public QDroneEntity(Class<? extends DroneEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userEntity = inits.isInitialized("userEntity") ? new com.example.sirius_restapi.user.domain.QUserEntity(forProperty("userEntity")) : null;
    }

}

