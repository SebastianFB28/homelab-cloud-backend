package com.homelab.cloud.application.port.in;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UploadAvatarUseCase {

    void execute(UUID userId, MultipartFile file);
}
