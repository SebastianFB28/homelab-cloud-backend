package com.homelab.cloud.application.service;

import com.homelab.cloud.application.port.in.IInitializeStorageQuotaUseCase;
import com.homelab.cloud.application.port.out.EventPublisherPort;
import com.homelab.cloud.application.port.out.StorageQuotaRepositoryPort;
import com.homelab.cloud.domain.model.StorageQuota;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class InitializeStorageQuotaService implements IInitializeStorageQuotaUseCase {


    private final StorageQuotaRepositoryPort storageQuotaRepositoryPort;

    @Override
    public void initializeDefaultQuota(UUID userId) {

        StorageQuota newQuota = StorageQuota.createDefault(userId);
        storageQuotaRepositoryPort.save(newQuota);

    }
}
