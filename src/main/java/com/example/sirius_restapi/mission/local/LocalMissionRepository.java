package com.example.sirius_restapi.mission.local;

import com.example.sirius_restapi.mission.local.domain.LocalMissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalMissionRepository extends JpaRepository<LocalMissionEntity,Integer> {
}
