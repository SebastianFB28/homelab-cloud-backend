package com.homelab.cloud.application.port.in;

import com.homelab.cloud.presentation.dto.storage.DownloadedFile;

import java.util.UUID;

public interface DownloadFileUseCase {

    DownloadedFile downloadFile(UUID fileId, UUID userId);


}
