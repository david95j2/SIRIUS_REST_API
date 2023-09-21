package com.example.sirius_restapi.mission.local.domain;

import com.example.sirius_restapi.inspection.analysis.domain.InspectionEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "local_missions")
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocalMissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    private String name;
    @JsonFormat(pattern = "yyyy-HH-dd HH:mm:ss")
    private LocalDateTime regdate;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "inspection_id")
    private InspectionEntity inspectionEntity;

    public PatchLocalMissionRes toDto() {
        PatchLocalMissionRes patchLocalMissionRes = new PatchLocalMissionRes();
        patchLocalMissionRes.setId(this.id);
        patchLocalMissionRes.setName(this.name);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime combinedDateTime = this.regdate;
        String formattedDate = combinedDateTime.format(formatter);
        patchLocalMissionRes.setRegdate(formattedDate);
        return patchLocalMissionRes;
    }

    @PrePersist
    public void prePersist() {
        this.regdate = LocalDateTime.now();
    }

    public static LocalMissionEntity from(PostLocalMissionReq postLocalMissionReq, InspectionEntity inspectionEntity) {
        return LocalMissionEntity.builder()
                .inspectionEntity(inspectionEntity)
                .name(postLocalMissionReq.getName())
                .build();
    }
}
