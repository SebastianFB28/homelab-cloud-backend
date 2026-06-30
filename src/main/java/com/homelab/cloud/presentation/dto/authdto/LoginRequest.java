package com.homelab.cloud.presentation.dto.authdto;

/**
 * LoginRequest DTO
 * Data entry form for logging in a person
 */

public record LoginRequest(
        String email,
        String password
) {
}
