package com.homelab.cloud.infrastructure.adapter.out.postgres;

import com.homelab.cloud.application.port.out.FolderRepositoryPort;
import com.homelab.cloud.domain.storage.Folder;
import com.homelab.cloud.infrastructure.adapter.out.postgres.entity.FolderEntity;
import com.homelab.cloud.infrastructure.adapter.out.postgres.repository.FolderJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Component
public class FolderPersistenceAdapter implements FolderRepositoryPort {


    private final FolderJpaRepository repository;

    @Override
    public Folder save(Folder folder) {

       repository.save(toEntity(folder));

        return folder;
    }

    @Override
    public Optional<Folder> findById(UUID id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public boolean existsByIdAndOwnerId(UUID folderId, UUID ownerId) {
        return repository.existsByIdAndOwnerId(folderId, ownerId);
    }

    @Override
    public List<Folder> findByParentFolderIdAndOwnerId(UUID parentFolderId, UUID ownerId) {
        return repository.findByParentFolderIdAndOwnerId(parentFolderId, ownerId)
                .stream()
                .map(this::toDomain)
                .toList();
    }


    /**
     * Temporary methods, pending the implementation of mapstruct
     * @param entity
     * @return
     */
    private Folder toDomain(FolderEntity entity) {
        return Folder.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ownerId(entity.getOwnerId())
                .parentFolderId(entity.getParentFolderId())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    private FolderEntity toEntity(Folder folder) {
        return FolderEntity.builder()
                .id(folder.getId())
                .name(folder.getName())
                .ownerId(folder.getOwnerId())
                .parentFolderId(folder.getParentFolderId())
                .createdAt(folder.getCreatedAt())
                .build();
    }
}
