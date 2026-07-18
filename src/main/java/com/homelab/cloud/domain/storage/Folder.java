package com.homelab.cloud.domain.storage;

import lombok.Builder;
import lombok.Getter;


import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Folder  implements StorageNode{

    private final UUID id;
    private  String name;
    private final UUID ownerId;
    private  UUID parentFolderId;
    private final LocalDateTime createdAt;


    @Builder
    public Folder(UUID id, String name, UUID ownerId, UUID parentFolderId, LocalDateTime createdAt) {
        this.id = id != null ? id : UUID.randomUUID();
        this.name = name;
        this.ownerId = ownerId;
        this.parentFolderId = parentFolderId;
        this.createdAt = java.time.LocalDateTime.now();
    }

    public void rename(String newName){
        validateName(newName);
        this.name = newName;
    }

    public void moveTo(UUID newParentFolderId) {
        if (this.id.equals(newParentFolderId)) {
            throw new IllegalArgumentException("Una carpeta no puede ser movida dentro de sí misma.");
        }
        this.parentFolderId = newParentFolderId;
    }


    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la carpeta no puede estar vacío.");
        }
        if (name.contains("/") || name.contains("\\")) {
            throw new IllegalArgumentException("El nombre de la carpeta contiene caracteres inválidos.");
        }
    }

    @Override
    public boolean isFolder() {
        return true;
    }
}
