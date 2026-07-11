package com.homelab.cloud.application.service;

import com.homelab.cloud.application.port.in.GetUserDashboardUseCase;
import com.homelab.cloud.application.port.out.StorageQuotaRepositoryPort;
import com.homelab.cloud.domain.model.StorageQuota;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class GetUserDashboardService  implements GetUserDashboardUseCase {


    private final StorageQuotaRepositoryPort storageQuotaRepositoryPort;

    @Override
    public StorageQuota execute(UUID userId) {

        return storageQuotaRepositoryPort.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cuota de almacenamiento no encontrada para el usuario"));
    }


}
