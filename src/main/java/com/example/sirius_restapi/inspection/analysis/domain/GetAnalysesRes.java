package com.example.sirius_restapi.inspection.analysis.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class GetAnalysesRes {
    Integer id;
    String regdate;
    String status;
    String ai_type;
}
