package com.homelab.cloud.application.port.in;

import java.util.UUID;

public interface IUpdateStorageQuotaUseCase {

    void update (UUID userId);
}
