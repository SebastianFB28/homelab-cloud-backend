package com.homelab.cloud.application.service;

import com.homelab.cloud.application.port.in.UploadFileUseCase;
import com.homelab.cloud.application.port.out.FileItemRepositoryPort;
import com.homelab.cloud.application.port.out.FolderRepositoryPort;
import com.homelab.cloud.application.port.out.PhysicalStoragePort;
import com.homelab.cloud.application.port.out.StorageQuotaRepositoryPort;
import com.homelab.cloud.domain.model.StorageQuota;
import com.homelab.cloud.domain.storage.FileItem;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
public class UploadFileService implements UploadFileUseCase {

    private final FolderRepositoryPort folderRepositoryPort;
    private final FileItemRepositoryPort fileItemRepositoryPort;
    private final PhysicalStoragePort physicalStoragePort;
    private final StorageQuotaRepositoryPort storageQuotaRepositoryPort;

    @Override
    public FileItem uploadFile(InputStream inputStream, String filename, Long size, String mimeType, UUID ownerId, UUID parentFolderId) {

        if (parentFolderId !=null) {
            if (!folderRepositoryPort.existsByIdAndOwnerId(parentFolderId, ownerId)) {
                throw new IllegalArgumentException("La carpeta no existe");
            }
        }

        // Optenemos la cuota de almacenamiento del usuario y verificamos si tiene suficiente espacio
        StorageQuota storageQuota = storageQuotaRepositoryPort.findByUserId(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("No tienes plan de almacenamiento, cotacta con el administrador "));


        storageQuota.consumeSpace(size);

        String physicalPath = null;

        try {
            // 3. Guardar el archivo físicamente en el disco duro
            physicalPath = physicalStoragePort.saveFile(inputStream, filename, ownerId.toString());

            // 4. Registrar la metadata en PostgreSQL
            FileItem fileItem = FileItem.builder()
                    .id(UUID.randomUUID())
                    .name(filename)
                    .sizeInBytes(size)
                    .mimeType(mimeType)
                    .ownerId(ownerId)
                    .parentFolderId(parentFolderId)
                    .physicalPath(physicalPath)
                    .build();


            FileItem savedFile = fileItemRepositoryPort.save(fileItem);

            // 5. Persistir el nuevo estado de la cuota consumida
            storageQuotaRepositoryPort.save(storageQuota);

            return savedFile;

        } catch (Exception e) {
            // Rollback manual del archivo físico en caso de falla de base de datos
            if (physicalPath != null) {
                physicalStoragePort.deleteFile(physicalPath);
            }
            throw new RuntimeException("Error en el flujo de subida de archivo. Almacenamiento revertido.", e);

        }
    }

}
