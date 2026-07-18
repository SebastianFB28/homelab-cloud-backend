package com.homelab.cloud.presentation.dto.storage;

import java.io.InputStream;

public record DownloadedFile(
        String fileName,
        String mimeType,
        long sizeInBytes,
        InputStream inputStream
) {
}
