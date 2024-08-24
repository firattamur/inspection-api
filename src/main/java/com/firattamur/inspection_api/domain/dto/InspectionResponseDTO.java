package com.firattamur.inspection_api.domain.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record InspectionResponseDTO(
        String carId,
        Long inspectionId,
        LocalDateTime createdAt,
        List<QuestionResponseDTO> questions
) {

}
