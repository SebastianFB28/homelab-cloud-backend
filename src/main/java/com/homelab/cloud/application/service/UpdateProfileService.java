package com.homelab.cloud.application.service;

import com.homelab.cloud.application.port.in.UpdateProfileUseCase;
import com.homelab.cloud.application.port.out.PasswordEncodePort;
import com.homelab.cloud.application.port.out.UserRepositoryPort;
import com.homelab.cloud.domain.exceptions.UserNotFoundException;
import com.homelab.cloud.domain.model.User;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class UpdateProfileService  implements UpdateProfileUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncodePort passwordEncodePort;

    @Override
    public void execute(UUID userId, String newNickname, String rawPassword) {

        User user = userRepositoryPort.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con ID: " + userId));


        String encodedPassword = (rawPassword != null && !rawPassword.isBlank())
                ? passwordEncodePort.encode(rawPassword)
                : null;

        // 3. Pasamos los datos limpios al dominio
        user.updateProfile(newNickname, encodedPassword);

        // 4. Guardamos los cambios en la BD
        userRepositoryPort.save(user);
    }
}
