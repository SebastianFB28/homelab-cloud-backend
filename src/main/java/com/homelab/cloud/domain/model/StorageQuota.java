package com.homelab.cloud.domain.model;

import com.homelab.cloud.domain.enums.StoragePlan;
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
    private StoragePlan storagePlan;


    // Factory method para inicializar la cuota de un nuevo usuario
    public static StorageQuota createDefault(UUID userId) {
        return StorageQuota.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .maxCapacityInBytes(StoragePlan.BASIC.getCapacityInBytes())
                .usedCapacityInBytes(0L)
                .storagePlan(StoragePlan.BASIC)
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


    public void validateAndUpdateStoragePlan(StoragePlan newStoragePlan) {
        if (newStoragePlan == null) {
            throw new IllegalArgumentException("El nuevo plan de almacenamiento no puede ser nulo.");
        }
        if (newStoragePlan.getCapacityInBytes() < this.usedCapacityInBytes) {
            throw new IllegalArgumentException("El nuevo plan de almacenamiento no puede ser menor al espacio ya utilizado.");
        }
    }

    public void updateStoragePlan(StoragePlan newStoragePlan) {
        validateAndUpdateStoragePlan(newStoragePlan);
        this.storagePlan = newStoragePlan;
        this.maxCapacityInBytes = newStoragePlan.getCapacityInBytes();
    }
}