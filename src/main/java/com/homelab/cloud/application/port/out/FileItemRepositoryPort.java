package com.homelab.cloud.application.port.out;

import com.homelab.cloud.domain.storage.FileItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FileItemRepositoryPort {

    FileItem save(FileItem fileItem);

    Optional<FileItem> findById(UUID id);

    /**
     * Checks if a file item exists with the given ID and owner ID.
     * @param parentFolderId
     * @param ownerId
     * @return
     */
    List<FileItem> findByParentFolderIdAndOwnerId(UUID parentFolderId, UUID ownerId);
}
