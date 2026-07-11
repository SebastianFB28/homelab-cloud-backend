package com.homelab.cloud.infrastructure.adapter.out.images;

import com.homelab.cloud.application.port.out.LoadAvatarPort;
import com.homelab.cloud.domain.valueobject.AvatarImage;
import com.homelab.cloud.infrastructure.adapter.out.storage.StorageProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileSystemAvatarAdapter implements LoadAvatarPort {

    private  final StorageProperties storageProperties;

    @Override
    public Optional<AvatarImage> loadAvatar(UUID userId) {

        Path rootPath = Paths.get(storageProperties.getAvatarDir());
        Path avatarPath = rootPath.resolve(userId.toString() + ".jpg");

        try {
            // 1. Si el usuario tiene su propia foto, la leemos y la devolvemos
            if (Files.exists(avatarPath)) {
                byte[] content = Files.readAllBytes(avatarPath);
                return Optional.of(new AvatarImage(content, AvatarImage.DEFAULT_MIME_TYPE));
            }

            // 2. Si NO existe (nunca subió una o la borró), cargamos la genérica
            ClassPathResource defaultImage = new ClassPathResource("static/default-avatar.jpg");

            // Extraemos los bytes de la imagen genérica incrustada en el proyecto
            byte[] defaultContent = defaultImage.getInputStream().readAllBytes();

            return Optional.of(new AvatarImage(defaultContent, AvatarImage.DEFAULT_MIME_TYPE));

        } catch (IOException e) {
            // Si algo falla al leer el disco duro o los recursos, lanzamos la excepción de dominio
            throw new RuntimeException("Error al cargar la imagen para el usuario: " + userId, e);
        }
    }
}





