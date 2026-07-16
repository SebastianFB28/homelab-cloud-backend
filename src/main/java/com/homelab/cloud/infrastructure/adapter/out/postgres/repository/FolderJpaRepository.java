package com.homelab.cloud.infrastructure.adapter.out.postgres.repository;

import com.homelab.cloud.infrastructure.adapter.out.postgres.entity.FolderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FolderJpaRepository extends JpaRepository<FolderEntity, UUID> {

    boolean existsByIdAndOwnerId(UUID id, UUID ownerId);

    List<FolderEntity> findByParentFolderIdAndOwnerId(UUID parentFolderId, UUID ownerId);
}
