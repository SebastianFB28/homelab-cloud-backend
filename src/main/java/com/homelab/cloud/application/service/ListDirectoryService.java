package com.homelab.cloud.application.service;

import com.homelab.cloud.application.port.in.ListDirectoryUseCase;
import com.homelab.cloud.application.port.out.FileItemRepositoryPort;
import com.homelab.cloud.application.port.out.FolderRepositoryPort;
import com.homelab.cloud.domain.storage.StorageNode;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ListDirectoryService implements ListDirectoryUseCase {


    private final FileItemRepositoryPort fileItemRepositoryPort;
    private final FolderRepositoryPort folderRepositoryPort;


    @Override
    public List<StorageNode> listDirectory(UUID folderId, UUID ownerId) {
        // Si folderId es null, listamos la raíz (root). Si no, verificamos que la carpeta exista.
        if (folderId != null) {
            if (!folderRepositoryPort.existsByIdAndOwnerId(folderId, ownerId)) {
                throw new IllegalArgumentException("El directorio solicitado no existe o no tienes acceso.");
            }
        }

        List<StorageNode> content = new ArrayList<>();

        // Gracias al patrón Composite, metemos tanto carpetas como archivos en la misma lista
        content.addAll(folderRepositoryPort.findByParentFolderIdAndOwnerId(folderId, ownerId));
        content.addAll(fileItemRepositoryPort.findByParentFolderIdAndOwnerId(folderId, ownerId));

        return content;
    }
}
