package com.homelab.cloud.presentation.dto.AuthDto;

/**
 *  RegisterRequest DTO
 *  Data entry form for registering a person
 */

public record RegisterRequest(
        String email,
        String password,
        String nickname
) {
}
