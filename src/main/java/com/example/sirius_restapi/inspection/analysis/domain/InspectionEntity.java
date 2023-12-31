package com.example.sirius_restapi.inspection.analysis.domain;

import com.example.sirius_restapi.map.domain.MapGroupEntity;
import com.example.sirius_restapi.mission.plan.domain.PlansEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "inspections")
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InspectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String name;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "map_group_id")
    private MapGroupEntity mapGroupEntity;

    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "inspectionEntity")
    private List<PlansEntity> localMissionEntities;

    public static InspectionEntity from(PostInspectionReq postInspectionReq, MapGroupEntity mapGroupEntity) {
        DateTimeFormatter convertDate = DateTimeFormatter.ofPattern("yyyyMMdd");
        return InspectionEntity.builder()
                .mapGroupEntity(mapGroupEntity)
                .name(postInspectionReq.getName())
                .date(LocalDate.parse(postInspectionReq.getDate(),convertDate))
                .build();
    }
}
