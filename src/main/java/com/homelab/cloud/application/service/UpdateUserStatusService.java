package com.homelab.cloud.application.service;


import com.homelab.cloud.application.port.in.IUpdateUserStatusUseCase;
import com.homelab.cloud.application.port.out.UserRepositoryPort;
import com.homelab.cloud.domain.enums.AccessStatus;
import com.homelab.cloud.domain.exceptions.UserNotFoundException;
import com.homelab.cloud.domain.model.User;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class UpdateUserStatusService  implements IUpdateUserStatusUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public void updateUserStatus(UUID userId, AccessStatus newStatus) {

        User user = userRepositoryPort.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuario con ID =" + userId + " no existe."));

        // Modificamos el estado en nuestro dominio puro
        user.changeAccessStatus(newStatus);

        // Guardamos
        userRepositoryPort.save(user);
    }
}
