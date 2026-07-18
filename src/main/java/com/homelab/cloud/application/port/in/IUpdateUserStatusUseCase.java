package com.homelab.cloud.application.port.in;

import java.util.UUID;
import com.homelab.cloud.domain.enums.AccessStatus;

public interface IUpdateUserStatusUseCase {

    void updateUserStatus(UUID userId, AccessStatus newStatus);

}
