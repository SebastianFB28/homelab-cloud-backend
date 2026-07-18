package com.homelab.cloud.application.port.in;

import com.homelab.cloud.domain.storage.FileItem;

import java.io.InputStream;
import java.util.UUID;

public interface UploadFileUseCase {

    FileItem uploadFile(InputStream inputStream, String filename, Long size, String mimeType, UUID ownerId, UUID parentFolderId);
}
