package com.homelab.cloud.infrastructure.adapter.out.postgres;

import com.homelab.cloud.application.port.out.StorageQuotaRepositoryPort;
import com.homelab.cloud.domain.model.StorageQuota;
import com.homelab.cloud.infrastructure.adapter.out.postgres.entity.StorageQuotaEntity;
import com.homelab.cloud.infrastructure.adapter.out.postgres.repository.SpringDataStorageQuotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StorageQuotaPersistenceAdapter implements StorageQuotaRepositoryPort {

    private final SpringDataStorageQuotaRepository repository;

    @Override
    public StorageQuota save(StorageQuota storageQuota) {
        StorageQuotaEntity entity = StorageQuotaEntity.fromDomain(storageQuota);
        StorageQuotaEntity savedEntity = repository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<StorageQuota> findByUserId(UUID userId) {
        return repository.findByUserId(userId).map(StorageQuotaEntity::toDomain);
    }

    @Override
    public Optional<StorageQuota> findById(UUID id) {
        return repository.findById(id).map(StorageQuotaEntity::toDomain);
    }
}
