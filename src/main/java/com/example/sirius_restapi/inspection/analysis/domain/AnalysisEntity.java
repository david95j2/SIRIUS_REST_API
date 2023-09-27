package com.example.sirius_restapi.inspection.analysis.domain;

import com.example.sirius_restapi.map.domain.MapGroupEntity;
import com.example.sirius_restapi.mission.local.domain.LocalMissionEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "analyses")
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnalysisEntity {
    @Id
    private Integer id;
    private Integer status;
    @JsonFormat(pattern = "yyyy-HH-dd HH:mm:ss")
    private LocalDateTime regdate;
    @Column(name = "ai_type")
    private String aiType;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "inspection_id")
    private InspectionEntity inspectionEntity;

    @PrePersist
    public void prePersist() {
        this.regdate = LocalDateTime.now();
    }

    public GetAnalysesRes toDto() {
        GetAnalysesRes getAnalysesRes = new GetAnalysesRes();
        getAnalysesRes.setId(this.id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = this.regdate.format(formatter);
        getAnalysesRes.setRegdate(formattedDate);
        if (this.getStatus() == 1) {
            getAnalysesRes.setStatus("완료");
        } else {
            getAnalysesRes.setStatus("진행중");
        }
        getAnalysesRes.setAi_type(this.aiType);
        return getAnalysesRes;
    }

}
