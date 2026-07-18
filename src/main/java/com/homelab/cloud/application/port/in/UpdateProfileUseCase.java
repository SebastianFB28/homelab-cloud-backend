package com.homelab.cloud.application.port.in;

import java.util.UUID;

public interface UpdateProfileUseCase {

    void execute(UUID userId, String newNickname, String rawPassword);

}
