package com.homelab.cloud.domain.storage;

import java.time.LocalDateTime;
import java.util.UUID;

public interface StorageNode {
    UUID getId();
    String getName();
    UUID getOwnerId();
    UUID getParentFolderId();
    LocalDateTime getCreatedAt();
    boolean isFolder();
}
