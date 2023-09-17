package com.example.sirius_restapi.mission.local.domain;

import com.example.sirius_restapi.inspection.analysis.domain.InspectionEntity;
import com.example.sirius_restapi.map.domain.MapGroupEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "fitting_group")
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FittingGroupEntity {
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

    public PatchFittingsRes toDto() {
        PatchFittingsRes patchFittingsRes = new PatchFittingsRes();
        patchFittingsRes.setId(this.id);
        patchFittingsRes.setName(this.name);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime combinedDateTime = this.regdate;
        String formattedDate = combinedDateTime.format(formatter);
        patchFittingsRes.setRegdate(formattedDate);
        return patchFittingsRes;
    }

    @PrePersist
    public void prePersist() {
        this.regdate = LocalDateTime.now();
    }
}
