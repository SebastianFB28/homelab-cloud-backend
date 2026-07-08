package com.homelab.cloud.infrastructure.adapter.out.postgres.repository;

import com.homelab.cloud.infrastructure.adapter.out.postgres.entity.StorageQuotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataStorageQuotaRepository extends JpaRepository<StorageQuotaEntity, UUID> {
    Optional<StorageQuotaEntity> findByUserId(UUID userId);
}