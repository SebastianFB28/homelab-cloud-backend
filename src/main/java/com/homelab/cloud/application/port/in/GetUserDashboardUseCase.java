package com.homelab.cloud.application.port.in;


import java.util.UUID;
import com.homelab.cloud.domain.model.StorageQuota;

public interface GetUserDashboardUseCase {

    StorageQuota execute(UUID userId);
}
