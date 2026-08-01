package com.homelab.cloud.application.service;

import com.homelab.cloud.application.port.in.IUpdateStorageQuotaUseCase;
import com.homelab.cloud.application.port.out.StorageQuotaRepositoryPort;
import com.homelab.cloud.domain.enums.StoragePlan;
import com.homelab.cloud.domain.model.StorageQuota;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class UpdateStorageQuotaService implements IUpdateStorageQuotaUseCase {

    private final StorageQuotaRepositoryPort storageQuotaRepositoryPort;

    @Override
    public void update(UUID userId, StoragePlan storagePlan) {


        StorageQuota storageQuota = storageQuotaRepositoryPort.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("El usuario no tiene plan de almacenameinto: " + userId));

        storageQuota.updateStoragePlan(storagePlan);


        storageQuotaRepositoryPort.save(storageQuota);

    }
}
