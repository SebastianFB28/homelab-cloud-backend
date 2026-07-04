package com.homelab.cloud.application.service;

import com.homelab.cloud.application.port.in.IUpdateUserByAdminUseCase;
import com.homelab.cloud.application.port.out.PasswordEncodePort;
import com.homelab.cloud.application.port.out.UserRepositoryPort;
import com.homelab.cloud.domain.enums.AccessStatus;
import com.homelab.cloud.domain.enums.Role;
import com.homelab.cloud.domain.exceptions.UserNotFoundException;
import com.homelab.cloud.domain.model.User;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class UpdateUserByAdminService implements IUpdateUserByAdminUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncodePort passwordEncodePort;


    /**
     * We retrieve the entity from the database, check if the password has
     * changed—encrypting it if it has—then modify and save it.
     * @param userId
     * @param nickname
     * @param rawPassword
     * @param role
     * @param status
     */
    @Override
    public void update (UUID userId, String nickname, String rawPassword, Role role, AccessStatus status){

        User user = userRepositoryPort.findById(userId).
                orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));

        String passwordEncoded = null;

        if (rawPassword != null && !rawPassword.isBlank()) {
            passwordEncoded = passwordEncodePort.encode(rawPassword);
        }

        user.updateByAdmin(nickname, passwordEncoded, role, status);

        userRepositoryPort.save(user);
    }
}