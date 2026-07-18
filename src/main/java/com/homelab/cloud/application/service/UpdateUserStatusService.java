package com.homelab.cloud.application.service;


import com.homelab.cloud.application.event.UserApprovedEvent;
import com.homelab.cloud.application.port.in.IUpdateUserStatusUseCase;
import com.homelab.cloud.application.port.out.EventPublisherPort;
import com.homelab.cloud.application.port.out.UserRepositoryPort;
import com.homelab.cloud.domain.enums.AccessStatus;
import com.homelab.cloud.domain.exceptions.UserNotFoundException;
import com.homelab.cloud.domain.model.User;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class UpdateUserStatusService  implements IUpdateUserStatusUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final EventPublisherPort eventPublisherPort;

    @Override
    public void updateUserStatus(UUID userId, AccessStatus newStatus) {

        User user = userRepositoryPort.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuario con ID =" + userId + " no existe."));

        if (user.validateStatus(newStatus)) {
            return;
        }

        // 1. Evaluamos si es un evento de aprobación ANTES de cambiar el estado
        boolean shouldPublishEvent = user.wasApprovedEvent(newStatus);

        // 2. Cambiamos el estado
        user.changeAccessStatus(newStatus);

        // 3. Guardamos
        userRepositoryPort.save(user);

        // 4. Disparamos el evento si la evaluación previa fue verdadera
        if (shouldPublishEvent){
            eventPublisherPort.publishUserApproved(new UserApprovedEvent(user.getId()));
        }
    }
}
