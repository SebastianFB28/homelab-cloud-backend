package com.homelab.cloud.presentation.dto.storage;

import java.util.UUID;

public record CreateFolderRequest(
        String name,
        UUID parentFolderId
) {
}
