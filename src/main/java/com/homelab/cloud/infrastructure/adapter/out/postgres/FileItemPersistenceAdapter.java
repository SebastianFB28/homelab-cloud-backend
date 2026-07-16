package com.homelab.cloud.infrastructure.adapter.out.postgres;

import com.homelab.cloud.application.port.out.FileItemRepositoryPort;

import com.homelab.cloud.domain.storage.FileItem;

import com.homelab.cloud.infrastructure.adapter.out.postgres.entity.FileItemEntity;

import com.homelab.cloud.infrastructure.adapter.out.postgres.repository.FileItemJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Component
public class FileItemPersistenceAdapter implements FileItemRepositoryPort {

    private final FileItemJpaRepository repository;

    @Override
    public FileItem save(FileItem fileItem) {

        FileItemEntity entity = toEntity(fileItem);

        repository.save(entity);

        return fileItem;
    }

    @Override
    public Optional<FileItem> findById(UUID id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<FileItem> findByIdAndOwnerId(UUID id, UUID ownerId) {
        return repository.findByIdAndOwnerId(id, ownerId).map(this::toDomain);
    }

    @Override
    public List<FileItem> findByParentFolderIdAndOwnerId(UUID parentFolderId, UUID ownerId) {
        return repository.findByParentFolderIdAndOwnerId(parentFolderId, ownerId)
                .stream()
                .map(this::toDomain)
                .toList();
    }


    private FileItem toDomain(FileItemEntity entity) {
        return FileItem.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ownerId(entity.getOwnerId())
                .parentFolderId(entity.getParentFolderId())
                .createdAt(entity.getCreatedAt())
                .sizeInBytes(entity.getSizeInBytes())
                .mimeType(entity.getMimeType())
                .physicalPath(entity.getPhysicalPath())
                .build();
    }

    private FileItemEntity toEntity(FileItem file){
        return FileItemEntity.builder()
                .id(file.getId())
                .name(file.getName())
                .ownerId(file.getOwnerId())
                .parentFolderId(file.getParentFolderId())
                .createdAt(file.getCreatedAt())
                .sizeInBytes(file.getSizeInBytes())
                .mimeType(file.getMimeType())
                .physicalPath(file.getPhysicalPath())
                .build();
    }
}
