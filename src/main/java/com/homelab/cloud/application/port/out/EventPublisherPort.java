package com.homelab.cloud.application.port.out;

import com.homelab.cloud.application.event.UserApprovedEvent;

public interface EventPublisherPort {

    void publishUserApproved(UserApprovedEvent event);
}

