package com.homelab.cloud.infrastructure.adapter.in.event;

import com.homelab.cloud.application.event.UserApprovedEvent;
import com.homelab.cloud.application.port.in.IInitializeStorageQuotaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserApprovedEventListener {

    private final IInitializeStorageQuotaUseCase initializeStorageQuotaUseCase;

    @EventListener
    public void onUserApproved(UserApprovedEvent event) {
        initializeStorageQuotaUseCase.initializeDefaultQuota(event.userID());
    }
}
