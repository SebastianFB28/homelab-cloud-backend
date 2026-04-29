package com.homelab.cloud.presentation.dto.AuthDto;

/**
 * LoginRequest DTO
 * Data entry form for logging in a person
 */

public record LoginRequest(
        String email,
        String password
) {
}
