package com.homelab.cloud.application.event;

import java.util.UUID;

public record UserApprovedEvent(UUID userID) {
}
