package com.homelab.cloud.domain.enums;

import lombok.Getter;

@Getter
public enum StoragePlan {

    BASIC(10L * 1024 * 1024 * 1024),
    STANDARD(20L * 1024 * 1024 * 1024),
    PREMIUM(30L * 1024 * 1024 * 1024),
    ENTERPRISE(40L * 1024 * 1024 * 1024);

    private final long capacityInBytes;

    StoragePlan(long capacityInBytes) {
        this.capacityInBytes = capacityInBytes;
    }

}
