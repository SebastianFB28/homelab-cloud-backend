package com.homelab.cloud.application.service;

import com.homelab.cloud.application.port.in.GetAvatarUseCase;
import com.homelab.cloud.application.port.out.LoadAvatarPort;
import com.homelab.cloud.domain.valueobject.AvatarImage;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class GetAvatarService implements GetAvatarUseCase {

    private final LoadAvatarPort loadAvatarPort;


    @Override
    public AvatarImage getAvatar(String userId) {
        return loadAvatarPort.loadAvatar(UUID.fromString(userId))
                .orElseGet(() -> new AvatarImage(new byte[0], AvatarImage.DEFAULT_MIME_TYPE));
    }
}
