package com.homelab.cloud.presentation.controller;

import com.homelab.cloud.application.port.in.GetUserDashboardUseCase;
import com.homelab.cloud.application.port.in.UpdateProfileUseCase;
import com.homelab.cloud.domain.model.StorageQuota;
import com.homelab.cloud.infrastructure.adapter.out.security.CustomUserDetails;
import com.homelab.cloud.presentation.dto.userdto.UpdateProfileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.homelab.cloud.presentation.dto.userdto.UserDashboardQueryResponse;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class UserController  {

    private final GetUserDashboardUseCase getUserDashboardUseCase;
    private final UpdateProfileUseCase updateProfileUseCase;

    /**
     * Allows you to obtain the storage quota, free space, and available space for a user.
     * @param userDetails The details of the authenticated user.
     * @return Rereturns a response entity containing the user's dashboard information, including email, nickname, maximum capacity, used capacity, and available capacity.
     */
    @GetMapping("/dashboard")
    public ResponseEntity<UserDashboardQueryResponse> getDashboard(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        // 1. Llamamos al caso de uso usando el ID de la sesión
        StorageQuota quota = getUserDashboardUseCase.execute(userDetails.getId());

        // 2. Calculamos la capacidad disponible en caliente
        long availableBytes = quota.getMaxCapacityInBytes() - quota.getUsedCapacityInBytes();

        // 3. Cruzamos la info del Token con la info de la BD en el DTO final
        UserDashboardQueryResponse response = new UserDashboardQueryResponse(
                userDetails.getEmail(),
                userDetails.getNickname(),
                quota.getMaxCapacityInBytes(),
                quota.getUsedCapacityInBytes(),
                availableBytes
        );

        return ResponseEntity.ok(response);
    }

    /**
     * It allows updating details such as nickname and password for a user.
     * @param userDetails The details of the authenticated user.
     * @param request The request containing the new profile information.
     * @return A response entity indicating the result of the update operation.
     */
    @PutMapping("/profile")
    public ResponseEntity<UpdateProfileRequest> updateProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody UpdateProfileRequest request

    ){
      updateProfileUseCase.execute(
              userDetails.getId(),
              request.nickname(),
              request.password()
      );

      return ResponseEntity.noContent().build();
    }
}
