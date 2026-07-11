package com.homelab.cloud.presentation.dto.userdto;

public record UserDashboardQueryResponse(
        String email,
        String nickname,
        long maxCapacityInBytes,
        long usedCapacityInBytes,
        long availableCapacityInBytes
) {
}
