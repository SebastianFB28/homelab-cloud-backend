package com.homelab.cloud.application.port.in;

import com.homelab.cloud.domain.storage.StorageNode;

import java.util.List;
import java.util.UUID;

public interface ListDirectoryUseCase {

    List<StorageNode> listDirectory(UUID folderId, UUID ownerId);


}
