package com.homelab.cloud.presentation.dto.UserDto;

// import lombok
import lombok.Builder;

// import domain enums and role
import com.homelab.cloud.domain.enums.Role;
import com.homelab.cloud.domain.enums.AccessStatus;
import com.homelab.cloud.domain.model.User;
// import java util

import java.util.UUID;
import java.time.LocalDateTime;

// dto for user response
@Builder
public record UserResponse (
        UUID id,
        String email,
        Role role,
        AccessStatus status,
        String nickname,
        LocalDateTime createdAt
) {
    /**
     * Mapea un usuario del dominio puro a una respuesta DTO para la web.
     */
    public static UserResponse fromDomain(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .status(user.getStatus())
                .nickname(user.getNickname())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
