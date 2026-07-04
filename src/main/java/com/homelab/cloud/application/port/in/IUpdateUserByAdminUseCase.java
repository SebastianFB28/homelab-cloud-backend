package com.homelab.cloud.application.port.in;

import com.homelab.cloud.domain.enums.AccessStatus;
import com.homelab.cloud.domain.enums.Role;

import java.util.UUID;

public interface IUpdateUserByAdminUseCase {


    /**
     * update user by admin
     * @param userId
     * @param nickname
     * @param rawPassword
     * @param role
     * @param status
     */
    void update (UUID userId, String nickname, String rawPassword, Role role, AccessStatus status);

}
