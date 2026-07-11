package com.homelab.cloud.presentation.controller;

import com.homelab.cloud.application.port.in.GetAvatarUseCase;
import com.homelab.cloud.application.port.in.UploadAvatarUseCase;
import com.homelab.cloud.domain.valueobject.AvatarImage;
import com.homelab.cloud.infrastructure.adapter.out.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/storage")
public class StorageController {

    private final GetAvatarUseCase getAvatarUseCase;
    private final UploadAvatarUseCase uploadAvatarUseCase;

    // Constante para el límite de 2MB (2 * 1024 * 1024 bytes)
    private static final long MAX_AVATAR_SIZE = 2097152L;

    @PostMapping("/save-avatar")
    public ResponseEntity<String> uploadAvatar(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("El archivo no puede estar vacío");
        }

        // Validación exclusiva para el avatar (fail-fast)
        if (file.getSize() > MAX_AVATAR_SIZE) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                    .body("El avatar supera el límite permitido de 2MB");
        }

        try {
            // Pasamos únicamente el InputStream nativo al core de nuestra app
            uploadAvatarUseCase.execute(userDetails.getId(), file.getInputStream());
            return ResponseEntity.ok("Avatar actualizado correctamente");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al leer el archivo subido");
        }
    }

    @GetMapping("/get-avatar")
    public ResponseEntity<byte[]> getAvatar(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        AvatarImage avatar = getAvatarUseCase.getAvatar(userDetails.getId().toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.mimeType()));
        headers.setCacheControl("public, max-age=86400");

        return new ResponseEntity<>(avatar.content(), headers, HttpStatus.OK);
    }

}
