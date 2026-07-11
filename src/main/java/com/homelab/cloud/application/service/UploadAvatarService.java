package com.homelab.cloud.application.service;

import com.homelab.cloud.application.port.in.UploadAvatarUseCase;
import com.homelab.cloud.infrastructure.adapter.out.storage.StorageProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@RequiredArgsConstructor
public class UploadAvatarService implements UploadAvatarUseCase {

    private final StorageProperties storageProperties;

    @Override
    public void execute(UUID userId, MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("El archivo no puede estar vacío");
        }

        try {
            // 1. Asegurar que la carpeta exista en el disco duro. Si no existe, la crea.
            Path directoryPath = Paths.get(storageProperties.getAvatarDir());
            Files.createDirectories(directoryPath);

            // 2. Leer la imagen subida (no importa si es PNG, JPG, etc.)
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            if (originalImage == null) {
                throw new IllegalArgumentException("El archivo subido no es una imagen válida");
            }

            // 3. TRUCO DE TRANSPARENCIA: Creamos un lienzo en blanco con el mismo tamaño
            BufferedImage newJpgImage = new BufferedImage(
                    originalImage.getWidth(),
                    originalImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB // Formato estándar RGB (fuerza a JPG)
            );

            // Pintamos el fondo de blanco y dibujamos la imagen original encima
            Graphics2D graphics = newJpgImage.createGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, newJpgImage.getWidth(), newJpgImage.getHeight());
            graphics.drawImage(originalImage, 0, 0, null);
            graphics.dispose();

            // 4. Definir la ruta final del archivo: /ruta/del/directorio/id-del-usuario.jpg
            File destinationFile = directoryPath.resolve(userId.toString() + ".jpg").toFile();

            // 5. Guardar físicamente en el disco duro como "jpg"
            ImageIO.write(newJpgImage, "jpg", destinationFile);

        } catch (IOException e) {
            throw new RuntimeException("Error crítico al guardar el avatar en el disco", e);
        }
    }
}