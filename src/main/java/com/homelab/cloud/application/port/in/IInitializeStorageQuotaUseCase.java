package com.homelab.cloud.application.port.in;

import java.util.UUID;

public interface IInitializeStorageQuotaUseCase {

    void initializeDefaultQuota(UUID userId);

}
