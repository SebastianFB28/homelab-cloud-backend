package com.homelab.cloud.presentation.dto.authdto;

/**
 * AuthResponse DTO
 * Response for authentication toContain JWT token
 */

public record AuthResponse(
        String token
) {
}
