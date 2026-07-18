package com.homelab.cloud.application.service;

import com.homelab.cloud.application.port.in.DownloadFileUseCase;
import com.homelab.cloud.application.port.out.FileItemRepositoryPort;
import com.homelab.cloud.application.port.out.PhysicalStoragePort;
import com.homelab.cloud.domain.storage.FileItem;
import com.homelab.cloud.presentation.dto.storage.DownloadedFile;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
public class DownloadFileService  implements DownloadFileUseCase {

    private final FileItemRepositoryPort fileItemRepositoryPort;
    private final PhysicalStoragePort physicalStoragePort;

    @Override
    public DownloadedFile downloadFile(UUID fileId, UUID userId) {

        FileItem fileItem = fileItemRepositoryPort.findByIdAndOwnerId(fileId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Accesos denegados o archivo no encontrado"));


        InputStream inputStream = physicalStoragePort.loadFile(fileItem.getPhysicalPath());

        return new DownloadedFile(
                fileItem.getName(),
                fileItem.getMimeType(),
                fileItem.getSizeInBytes(),
                inputStream
        );
    }
}
