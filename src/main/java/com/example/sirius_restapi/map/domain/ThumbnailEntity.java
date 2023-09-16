package com.example.sirius_restapi.map.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "thumbnails")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ThumbnailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "thumbnail_path")
    private String thumbnailPath;
    @Column(name = "thumbnail_regdate")
    private String thumbnailRegdate;
    @Column(name = "thumbnail_last_regdate")
    private String thumbnailLastRegdate;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "location_id")
    private LocationEntity locationEntity;
}
