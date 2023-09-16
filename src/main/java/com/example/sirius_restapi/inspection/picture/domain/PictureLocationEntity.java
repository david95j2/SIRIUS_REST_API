package com.example.sirius_restapi.inspection.picture.domain;

import com.example.sirius_restapi.inspection.analysis.domain.InspectionEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "picture_locations")
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PictureLocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "pos_x")
    private Float posX;
    @Column(name = "pos_y")
    private Float posY;
    @Column(name = "pos_z")
    private Float posZ;
    private Float roll;
    private Float pitch;
    private Float yaw;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "picture_id")
    private PictureEntity pictureEntity;
}
