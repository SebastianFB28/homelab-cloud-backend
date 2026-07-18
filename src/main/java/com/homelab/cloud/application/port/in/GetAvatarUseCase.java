package com.homelab.cloud.application.port.in;

import com.homelab.cloud.domain.valueobject.AvatarImage;

public interface GetAvatarUseCase {

    AvatarImage getAvatar(String userId);

}
