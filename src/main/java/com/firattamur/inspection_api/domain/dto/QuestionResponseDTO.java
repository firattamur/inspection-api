package com.firattamur.inspection_api.domain.dto;

public record QuestionResponseDTO(
        Long id,
        String text,
        Integer orderNumber,
        AnswerResponseDTO answer
) {

}
