package com.example.sirius_restapi.map.domain;

import com.example.sirius_restapi.mission.global.domain.GlobalWayPointEntity;
import com.example.sirius_restapi.user.domain.UserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "locations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    private String location;
    private Float latitude;
    private Float longitude;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "locationEntity",cascade = CascadeType.REMOVE)
    private List<ThumbnailEntity> thumbnailEntities;

    public static LocationEntity from(PostLocationReq postLocationReq, UserEntity userEntity) {
        return LocationEntity.builder()
                .userEntity(userEntity)
                .location(postLocationReq.getLocation())
                .latitude(postLocationReq.getLatitude())
                .longitude(postLocationReq.getLongitude())
                .build();
    }

    public PatchLocationRes toDto() {
        PatchLocationRes patchLocationRes = new PatchLocationRes();
        patchLocationRes.setId(this.id);
        patchLocationRes.setLocation(this.location);
        patchLocationRes.setLatitude(this.latitude);
        patchLocationRes.setLongitude(this.longitude);
        return patchLocationRes;
    }
}
