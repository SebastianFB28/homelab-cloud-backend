package com.homelab.cloud.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class StorageQuota {
    private final UUID id;
    private final UUID userId;
    private long maxCapacityInBytes;
    private long usedCapacityInBytes;

    // initial space constant for a user
    public static final long DEFAULT_MAX_CAPACITY_BYTES = 10L * 1024 * 1024 * 1024;

    // Factory method para inicializar la cuota de un nuevo usuario
    public static StorageQuota createDefault(UUID userId) {
        return StorageQuota.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .maxCapacityInBytes(DEFAULT_MAX_CAPACITY_BYTES)
                .usedCapacityInBytes(0L)
                .build();
    }



    public boolean hasAvailableSpace(long sizeInBytes) {
        return (this.usedCapacityInBytes + sizeInBytes) <= this.maxCapacityInBytes;
    }

    public void consumeSpace(long sizeInBytes) {
        if (!hasAvailableSpace(sizeInBytes)) {
            throw new IllegalArgumentException("No hay suficiente espacio de almacenamiento disponible.");
        }
        this.usedCapacityInBytes += sizeInBytes;
    }

    public void releaseSpace(long sizeInBytes) {
        if (this.usedCapacityInBytes - sizeInBytes < 0) {
            this.usedCapacityInBytes = 0;
        } else {
            this.usedCapacityInBytes -= sizeInBytes;
        }
    }

    public void updateMaxCapacity(long newMaxCapacityInBytes) {
        if (newMaxCapacityInBytes < this.usedCapacityInBytes) {
            throw new IllegalArgumentException("La nueva capacidad no puede ser menor al espacio ya utilizado.");
        }
        this.maxCapacityInBytes = newMaxCapacityInBytes;
    }

    public void addExtraSpace(long extraSpaceInBytes) {
        if (extraSpaceInBytes <= 0) {
            throw new IllegalArgumentException("El espacio a añadir debe ser mayor a cero.");
        }
        this.maxCapacityInBytes += extraSpaceInBytes;
    }
}