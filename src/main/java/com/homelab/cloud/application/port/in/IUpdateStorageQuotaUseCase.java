package com.homelab.cloud.application.port.in;

import com.homelab.cloud.domain.enums.StoragePlan;

import java.util.UUID;

public interface IUpdateStorageQuotaUseCase {

    void update (UUID userId , StoragePlan storagePlan);
}
