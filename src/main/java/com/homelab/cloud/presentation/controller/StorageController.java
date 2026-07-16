package com.homelab.cloud.presentation.controller;

import com.homelab.cloud.application.port.in.*;
import com.homelab.cloud.domain.storage.FileItem;
import com.homelab.cloud.domain.storage.Folder;
import com.homelab.cloud.domain.storage.StorageNode;
import com.homelab.cloud.domain.valueobject.AvatarImage;
import com.homelab.cloud.infrastructure.adapter.out.security.CustomUserDetails;
import com.homelab.cloud.presentation.dto.storage.CreateFolderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/storage")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class StorageController {

    private final GetAvatarUseCase getAvatarUseCase;
    private final UploadAvatarUseCase uploadAvatarUseCase;
    private final CreateFolderUseCase createFolderUseCase;
    private final UploadFileUseCase uploadFileUseCase;
    private final ListDirectoryUseCase listDirectoryUseCase;

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

    @PostMapping("/folders")
    public ResponseEntity<Folder> createFolder(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CreateFolderRequest request) { // <-- ¡Ahora sí recibe un JSON!

        Folder newFolder = createFolderUseCase.createFolder(
                request.name(),
                userDetails.getId(),
                request.parentFolderId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(newFolder);
    }

    @PostMapping("/files/upload")
    public ResponseEntity<FileItem> uploadFile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false) UUID parentFolderId) {

        if (file.isEmpty()) {
            // A diferencia del avatar que devuelve un String, aquí lanzamos excepción
            // que podrías manejar con un ControllerAdvice global si ya tienes uno.
            throw new IllegalArgumentException("El archivo a subir no puede estar vacío.");
        }

        try {
            FileItem savedFile = uploadFileUseCase.uploadFile(
                    file.getInputStream(),
                    file.getOriginalFilename(),
                    file.getSize(),
                    file.getContentType(),
                    userDetails.getId(),
                    parentFolderId
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(savedFile);
        } catch (IOException e) {
            throw new RuntimeException("Error al procesar el archivo subido.", e);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<StorageNode>> listDirectory(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(required = false) UUID folderId) {

        List<StorageNode> directoryContent = listDirectoryUseCase.listDirectory(folderId, userDetails.getId());
        return ResponseEntity.ok(directoryContent);
    }

}
