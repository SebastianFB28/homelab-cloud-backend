package com.homelab.cloud.presentation.controller;

// import port in
import com.homelab.cloud.application.port.in.IGetUsersByStatusUseCase;
import com.homelab.cloud.application.port.in.IUpdateUserStatusUseCase;
// import dtos
import com.homelab.cloud.domain.enums.AccessStatus;
import com.homelab.cloud.presentation.dto.userdto.UserResponse;

// import lombok
import lombok.RequiredArgsConstructor;

// import springframework
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// import java util
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final IGetUsersByStatusUseCase getUsersByStatusUseCase;
    private final IUpdateUserStatusUseCase updateUserStatusUseCase;

    /**
     * Get users filter by status
     * @param status
     * @return
     */
    @GetMapping(params = "status")
    public ResponseEntity<List<UserResponse>> getPendingUsers(@RequestParam AccessStatus status) {
        List<UserResponse> response = getUsersByStatusUseCase.getUserByStatus(status)
                .stream()
                .map(UserResponse::fromDomain) // ¡Mira qué elegante y limpio queda gracias a tu idea!
                .toList();
        return ResponseEntity.ok(response);
    }


    /**
     * update user by status
     * Regen , no update user by status , status == banned
     * @param userId
     * @param newStatus
     * @return
     */
    @PatchMapping("/{userId}/status")
    public ResponseEntity<Void> updateUserStatus(
            @PathVariable UUID userId,
            @RequestParam AccessStatus newStatus) { // <-- Ojo: es @RequestParam, no @PathVariable

        updateUserStatusUseCase.updateUserStatus(userId, newStatus);

        return ResponseEntity.ok().build();
    }
}
