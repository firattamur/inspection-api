package com.firattamur.inspection_api.repository;

import com.firattamur.inspection_api.domain.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    public List<QuestionEntity> findAllByOrderByOrderNumberAsc();
}
