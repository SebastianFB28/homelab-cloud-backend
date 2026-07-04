package com.homelab.cloud.presentation.controller;

// import port in
import com.homelab.cloud.application.port.in.IDeleteUserUseCase;
import com.homelab.cloud.application.port.in.IGetUsersByStatusUseCase;
import com.homelab.cloud.application.port.in.IUpdateUserStatusUseCase;
import com.homelab.cloud.application.port.in.IUpdateUserByAdminUseCase;
// import dtos
import com.homelab.cloud.domain.enums.AccessStatus;
import com.homelab.cloud.presentation.dto.userdto.UserResponse;
import com.homelab.cloud.presentation.dto.userdto.AdminUpdateUserRequest;


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
    private final IUpdateUserByAdminUseCase updateUserByAdminUseCase;
    private final IDeleteUserUseCase deleteUserUseCase;


    /**
     * Get users filter by status
     * @param status
     * @return
     */
    @GetMapping(params = "status")
    public ResponseEntity<List<UserResponse>> getPendingUsers(@RequestParam AccessStatus status) {
        List<UserResponse> response = getUsersByStatusUseCase.getUserByStatus(status)
                .stream()
                .map(UserResponse::fromDomain)
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
            @RequestParam AccessStatus newStatus) {

        updateUserStatusUseCase.updateUserStatus(userId, newStatus);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserByAdmin (
            @PathVariable UUID userId,
            @RequestBody AdminUpdateUserRequest request){

        updateUserByAdminUseCase.update(
                userId,
                request.nickname(),
                request.password(),
                request.role(),
                request.status()
        );

        return ResponseEntity.ok().build();
    }

    /**
     * Physical deletion for a user
     * @param userID
     * @return
     */
    @DeleteMapping("/{userID}")
    public ResponseEntity<Void> deleteUserByAdmin (
            @PathVariable UUID userID){

        deleteUserUseCase.hardDelete(userID);
        return ResponseEntity.noContent().build();
    }

    // 2. SOFT DELETE (Borrado lógico)
    // URL en Postman: PATCH http://localhost:8080/api/v1/admin/users/{userId}/soft-delete
    @PatchMapping("/{userId}/soft-delete")
    public ResponseEntity<Void> softDeleteUser(@PathVariable UUID userId) {
        deleteUserUseCase.sofDelete(userId);
        return ResponseEntity.noContent().build();
    }

}
