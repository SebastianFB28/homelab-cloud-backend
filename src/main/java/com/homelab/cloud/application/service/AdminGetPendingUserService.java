package com.homelab.cloud.application.service;

// import port in GetPendingUsersUseCase
import com.homelab.cloud.application.port.in.GetPendingUsersUseCase;
// import port out UserRepositoryPort
import com.homelab.cloud.application.port.out.UserRepositoryPort;
// import domain model user
import com.homelab.cloud.domain.model.User;
// import emuns the user model
import com.homelab.cloud.domain.enums.AccessStatus;

// import lombok
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AdminGetPendingUserService implements GetPendingUsersUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public List<User> getPendingUsers() {
        // Get all users with status PENDING
        return userRepositoryPort.findByStatus(AccessStatus.PENDING);
    }

}
