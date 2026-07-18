package com.homelab.cloud.application.service;

import com.homelab.cloud.application.port.in.IDeleteUserUseCase;
import com.homelab.cloud.application.port.out.UserRepositoryPort;
import com.homelab.cloud.domain.exceptions.UserNotFoundException;
import com.homelab.cloud.domain.model.User;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class DeleteUserService  implements IDeleteUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public void sofDelete (UUID userId) {

        User user = userRepositoryPort.findById(userId).orElseThrow(()
                -> new UserNotFoundException("User not found"));

        user.sofDelete();

        userRepositoryPort.save(user);

    }

    @Override
    public void hardDelete(UUID userID) {
        userRepositoryPort.findById(userID).orElseThrow(()
                -> new UserNotFoundException("User not found"));

        userRepositoryPort.deleteById(userID);

    }
}
