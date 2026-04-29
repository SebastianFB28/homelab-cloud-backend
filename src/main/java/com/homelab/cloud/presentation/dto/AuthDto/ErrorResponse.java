package com.homelab.cloud.presentation.dto.AuthDto;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        int status,
        LocalDateTime timestamp
) {
}
