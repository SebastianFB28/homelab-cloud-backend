package com.homelab.cloud.domain.storage;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class FileItem implements StorageNode{

   private final UUID id;
   private String name;
   private final UUID ownerId;
   private UUID parentFolderId;
   private final LocalDateTime createdAt;

   //exclusive properties
    private final long sizeInBytes;
    private final String mimeType;
    private final String physicalPath;

    @Builder
    public FileItem(UUID id, String name, UUID ownerId, UUID parentFolderId, LocalDateTime createdAt, Long sizeInBytes, String mimeType, String physicalPath) {
        validateName(name);
        if (sizeInBytes == null || sizeInBytes < 0) {
            throw new IllegalArgumentException("El tamaño del archivo no puede ser negativo.");
        }

        this.id = id != null ? id : UUID.randomUUID();
        this.name = name;
        this.ownerId = ownerId;
        this.parentFolderId = parentFolderId;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.sizeInBytes = sizeInBytes;
        this.mimeType = mimeType;
        this.physicalPath = physicalPath;
    }

    public void rename(String newName) {
        validateName(newName);
        this.name = newName;
    }

    public void moveTo(UUID newParentFolderId) {
        this.parentFolderId = newParentFolderId;
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del archivo no puede estar vacío.");
        }
        if (name.contains("/") || name.contains("\\")) {
            throw new IllegalArgumentException("El nombre del archivo contiene caracteres inválidos.");
        }
    }

    @Override
    public boolean isFolder() {
        return false;
    }
}
