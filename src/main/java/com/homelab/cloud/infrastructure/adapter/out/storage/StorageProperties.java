package com.homelab.cloud.infrastructure.adapter.out.storage;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class StorageProperties {

    @Value("${homelab.storage.avatar-dir}")
    private String avatarDir;
}
