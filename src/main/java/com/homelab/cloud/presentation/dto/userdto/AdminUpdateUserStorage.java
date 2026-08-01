package com.homelab.cloud.presentation.dto.userdto;

import com.homelab.cloud.domain.enums.StoragePlan;

public record AdminUpdateUserStorage(
       StoragePlan storagePlan
) {
}
