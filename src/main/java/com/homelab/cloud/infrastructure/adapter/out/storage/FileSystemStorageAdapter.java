package com.homelab.cloud.infrastructure.adapter.out.storage;

import com.homelab.cloud.application.port.out.PhysicalStoragePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@AllArgsConstructor
@Component
public class FileSystemStorageAdapter implements PhysicalStoragePort {

    private final StorageProperties storageProperties;


    @Override
    public String saveFile(InputStream inputStream, String originalFilename, String ownerId) {
        try {
            Path userDir = Paths.get(storageProperties.getFilesDir(), ownerId);
            if (!Files.exists(userDir)) {
                Files.createDirectories(userDir);
            }

            String physicalFileName = UUID.randomUUID().toString() + "_" + originalFilename;
            Path targetPath = userDir.resolve(physicalFileName);

            Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);

            return targetPath.toString(); // Devolvemos la ruta absoluta para guardarla en BD
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo físico en disco", e);
        }
    }

    @Override
    public void deleteFile(String physicalPath) {
        try {
            Files.deleteIfExists(Paths.get(physicalPath));
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar el archivo físico: " + physicalPath, e);
        }
    }

    @Override
    public InputStream loadFile(String physicalPath) {
        try{
            Path path = Paths.get(physicalPath);
            if (!Files.exists(path)) {
                throw new IllegalArgumentException("El archivo físico no existe en el almacenamiento: " + physicalPath);
            }
            return Files.newInputStream(path);

        }catch (IOException e){
            throw new RuntimeException("Error al leer el archivo físico del disco duro", e);
        }
    }
}
