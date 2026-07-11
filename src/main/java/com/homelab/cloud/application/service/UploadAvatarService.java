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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@RequiredArgsConstructor
public class UploadAvatarService implements UploadAvatarUseCase {

    private final StorageProperties storageProperties;

    @Override
    public void execute(UUID userId, InputStream imageStream) {
        try {
            // 1. Asegurar que la carpeta exista
            Path directoryPath = Paths.get(storageProperties.getAvatarDir());
            Files.createDirectories(directoryPath);

            // 2. Leer la imagen desde el stream puro
            BufferedImage originalImage = ImageIO.read(imageStream);
            if (originalImage == null) {
                throw new IllegalArgumentException("El archivo subido no es una imagen válida o está dañado.");
            }

            // 3. Convertir a JPG (Manejando el fondo blanco para PNGs transparentes)
            BufferedImage newJpgImage = new BufferedImage(
                    originalImage.getWidth(),
                    originalImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB
            );

            Graphics2D graphics = newJpgImage.createGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, newJpgImage.getWidth(), newJpgImage.getHeight());
            graphics.drawImage(originalImage, 0, 0, null);
            graphics.dispose();

            // 4. Guardar físicamente
            File destinationFile = directoryPath.resolve(userId.toString() + ".jpg").toFile();
            ImageIO.write(newJpgImage, "jpg", destinationFile);

        } catch (IOException e) {
            throw new RuntimeException("Error crítico al procesar y guardar el avatar en el disco", e);
        }
    }
}