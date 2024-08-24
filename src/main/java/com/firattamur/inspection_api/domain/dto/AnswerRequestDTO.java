package com.firattamur.inspection_api.domain.dto;

import java.util.List;

public record AnswerRequestDTO(
        Long questionId,
        Boolean hasIssue,
        String description,
        List<InspectionPhotoRequestDTO> photos
) {

}
