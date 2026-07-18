package com.homelab.cloud.application.port.out;

import com.homelab.cloud.domain.valueobject.AvatarImage;

import java.util.Optional;
import java.util.UUID;

public interface LoadAvatarPort {

    Optional<AvatarImage> loadAvatar(UUID userId);

}
