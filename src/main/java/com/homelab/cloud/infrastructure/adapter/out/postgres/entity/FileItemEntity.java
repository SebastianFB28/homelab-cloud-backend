package com.homelab.cloud.infrastructure.adapter.out.postgres.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "homelab_file")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileItemEntity {

    @Id
    private UUID id;
    private String name;
    private UUID ownerId;
    private UUID parentFolderId;
    private LocalDateTime createdAt;
    private Long sizeInBytes;
    private String mimeType;
    private String physicalPath;

}
