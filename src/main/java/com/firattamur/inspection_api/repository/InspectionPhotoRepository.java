package com.firattamur.inspection_api.repository;

import com.firattamur.inspection_api.domain.entity.InspectionPhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InspectionPhotoRepository extends JpaRepository<InspectionPhotoEntity, Long> {
}
