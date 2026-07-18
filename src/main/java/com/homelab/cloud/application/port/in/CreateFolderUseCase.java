package com.homelab.cloud.application.port.in;

import com.homelab.cloud.domain.storage.Folder;

import java.util.UUID;

public interface CreateFolderUseCase {

    Folder createFolder(String name, UUID ownerId, UUID parentFolderId);

}
