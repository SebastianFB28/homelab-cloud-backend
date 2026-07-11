package com.homelab.cloud.presentation.controller;

import com.homelab.cloud.application.port.in.UploadAvatarUseCase;
import com.homelab.cloud.infrastructure.adapter.out.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/storage")
public class StorageController {

    private final UploadAvatarUseCase uploadAvatarUseCase;

    @PostMapping("/avatar")
    public ResponseEntity<String> uploadAvatar(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("file") MultipartFile file) {

        uploadAvatarUseCase.execute(userDetails.getId(), file);

        return ResponseEntity.ok("Avatar actualizado correctamente");
    }
}
