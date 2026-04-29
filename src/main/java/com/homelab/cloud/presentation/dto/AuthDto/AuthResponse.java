package com.homelab.cloud.presentation.dto.AuthDto;

/**
 * AuthResponse DTO
 * Response for authentication toContain JWT token
 */

public record AuthResponse(
        String token
) {
}
