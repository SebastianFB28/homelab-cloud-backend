package com.homelab.cloud.presentation.controller;

// import port in
import com.homelab.cloud.application.port.in.GetPendingUsersUseCase;

// import dtos
import com.homelab.cloud.presentation.dto.UserDto.UserResponse;

// import lombok
import lombok.RequiredArgsConstructor;

// import springframework
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import java util
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final GetPendingUsersUseCase getPendingUsersUseCase;

    @GetMapping("/status/pending")
    public ResponseEntity<List<UserResponse>> getPendingUsers() {
        List<UserResponse> response = getPendingUsersUseCase.getPendingUsers()
                .stream()
                .map(UserResponse::fromDomain) // ¡Mira qué elegante y limpio queda gracias a tu idea!
                .toList();
        return ResponseEntity.ok(response);
    }
}
