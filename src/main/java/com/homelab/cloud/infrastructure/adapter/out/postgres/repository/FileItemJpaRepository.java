package com.homelab.cloud.infrastructure.adapter.out.postgres.repository;

import com.homelab.cloud.infrastructure.adapter.out.postgres.entity.FileItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FileItemJpaRepository extends JpaRepository<FileItemEntity, UUID> {

    List<FileItemEntity> findByParentFolderIdAndOwnerId(UUID parentFolderId, UUID ownerId);
}
