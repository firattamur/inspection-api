package com.firattamur.inspection_api.domain.dto;

import java.util.List;

public record InspectionRequestDTO(
        String carId,
        List<AnswerRequestDTO> answers
) {

}
