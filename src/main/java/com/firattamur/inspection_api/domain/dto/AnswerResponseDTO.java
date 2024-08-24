package com.firattamur.inspection_api.domain.dto;

import java.util.List;

public record AnswerResponseDTO(
        Boolean hasIssue,
        String description,
        List<String> photoUrls
) {

}


