package com.homelab.cloud.application.service;

import com.homelab.cloud.application.port.in.CreateFolderUseCase;
import com.homelab.cloud.application.port.out.FolderRepositoryPort;
import com.homelab.cloud.domain.storage.Folder;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class CreateFolderService implements CreateFolderUseCase {


    private final FolderRepositoryPort repositoryPort;

    @Override
    public Folder createFolder(String name, UUID ownerId, UUID parentFolderId) {

        if (parentFolderId != null) {
            boolean exists = repositoryPort.existsByIdAndOwnerId(parentFolderId, ownerId);
            if (!exists) {
                throw new IllegalArgumentException("La carpeta destino no existe ");
            }
        }

        Folder folder = Folder.builder()
                .id(null)
                .name(name)
                .ownerId(ownerId)
                .parentFolderId(parentFolderId)
                .build();


        return repositoryPort.save(folder);
    }
}
