package com.example.sirius_restapi.map;

import com.example.sirius_restapi.map.domain.ThumbnailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThumbnailRepository extends JpaRepository<ThumbnailEntity,Integer> {
}
