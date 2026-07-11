package com.homelab.cloud.application.port.out;

import  com.homelab.cloud.domain.model.StorageQuota;

import java.util.Optional;
import java.util.UUID;

public interface StorageQuotaRepositoryPort {

    /**
     * Create the initial payment for an already active user.
     * @param storageQuota
     * @return
     */
    StorageQuota save(StorageQuota storageQuota);

    /**
     * obtains a user's share, available space, and used space through their ID
     * @param userId
     * @return
     */
    Optional<StorageQuota> findByUserId(UUID userId);

    /**
     * Retrieves a user's quota, available space, and used space using the quota ID.
     * @param id
     * @return
     */
    Optional<StorageQuota> findById(UUID id);

}
