package com.firattamur.inspection_api.exception;

import java.time.LocalDateTime;

public record APIError(
        String path,
        String message,
        int statusCode,
        LocalDateTime localDateTime
) {
}
