package com.homelab.cloud.application.port.out;

import  com.homelab.cloud.domain.model.StorageQuota;

import java.util.Optional;
import java.util.UUID;

public interface StorageQuotaRepositoryPort {

    StorageQuota save(StorageQuota storageQuota);

    Optional<StorageQuota> findByUserId(UUID userId);

    Optional<StorageQuota> findById(UUID id);

}
