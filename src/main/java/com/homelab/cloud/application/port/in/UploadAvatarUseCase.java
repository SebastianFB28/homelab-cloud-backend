package com.homelab.cloud.application.port.in;


import java.io.InputStream;
import java.util.UUID;

public interface UploadAvatarUseCase {

    void execute(UUID userId, InputStream imageStream);
}
