package com.homelab.cloud.infrastructure.adapter.out.event;

import com.homelab.cloud.application.event.UserApprovedEvent;
import com.homelab.cloud.application.port.out.EventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringEventPublisherAdapter implements EventPublisherPort {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publishUserApproved(UserApprovedEvent event) {
        // Traducimos el llamado del puerto al ecosistema de Spring
        applicationEventPublisher.publishEvent(event);
    }
}
