package com.homelab.cloud.application.port.out;

import com.homelab.cloud.domain.storage.Folder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FolderRepositoryPort {

    Folder save (Folder folder);

    Optional<Folder> findById (UUID id);

    /**
     * Checks if a folder exists with the given ID and owner ID.
     * @param folderId
     * @param ownerId
     * @return
     */
    boolean existsByIdAndOwnerId(UUID folderId, UUID ownerId);

    /**
     * Finds all folders that have the specified parent folder ID and owner ID.
     * @param parentFolderId
     * @param ownerId
     * @return
     */
    List<Folder> findByParentFolderIdAndOwnerId(UUID parentFolderId, UUID ownerId);
}
