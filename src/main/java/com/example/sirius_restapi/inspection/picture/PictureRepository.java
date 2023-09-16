package com.example.sirius_restapi.inspection.picture;


import com.example.sirius_restapi.inspection.picture.domain.PictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<PictureEntity, Integer> {
}
