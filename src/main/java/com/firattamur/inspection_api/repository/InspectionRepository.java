package com.firattamur.inspection_api.repository;


import com.firattamur.inspection_api.domain.entity.InspectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InspectionRepository extends JpaRepository<InspectionEntity, Long> {
    Optional<InspectionEntity> findFirstByCarIdOrderByCreatedAtDesc(String carId);
}
