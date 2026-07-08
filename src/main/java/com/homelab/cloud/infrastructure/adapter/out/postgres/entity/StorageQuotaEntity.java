package com.homelab.cloud.infrastructure.adapter.out.postgres.entity;

import com.homelab.cloud.domain.model.StorageQuota;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "storage_quota")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StorageQuotaEntity {

    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @Column(name = "max_capacity_bytes", nullable = false)
    private Long maxCapacityBytes;

    @Column(name = "used_capacity_bytes", nullable = false)
    private Long usedCapacityBytes;

    // Relación lazy con el usuario para mantener integridad referencial a nivel de JPA
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity user;

    // --- MAPPERS ---
    public static StorageQuotaEntity fromDomain(StorageQuota domain) {
        return StorageQuotaEntity.builder()
                .id(domain.getId())
                .userId(domain.getUserId())
                .maxCapacityBytes(domain.getMaxCapacityInBytes())
                .usedCapacityBytes(domain.getUsedCapacityInBytes())
                .build();
    }

    public StorageQuota toDomain() {
        return StorageQuota.builder()
                .id(this.id)
                .userId(this.userId)
                .maxCapacityInBytes(this.maxCapacityBytes)
                .usedCapacityInBytes(this.usedCapacityBytes)
                .build();
    }
}