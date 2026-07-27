CREATE TABLE homelab_user (
                              id UUID PRIMARY KEY,
                              email VARCHAR(255) NOT NULL UNIQUE,
                              password VARCHAR(255) NOT NULL,
                              role VARCHAR(50) NOT NULL,
                              access_status VARCHAR(50) NOT NULL,
                              nickname VARCHAR(255),
                              created_at TIMESTAMP
);

CREATE TABLE homelab_folder (
                                id UUID PRIMARY KEY,
                                name VARCHAR(255) NOT NULL,
                                owner_id UUID NOT NULL,
                                parent_folder_id UUID,
                                created_at TIMESTAMP,

                                CONSTRAINT fk_folder_owner
                                    FOREIGN KEY (owner_id)
                                        REFERENCES homelab_user(id)
                                        ON DELETE CASCADE,

                                CONSTRAINT fk_folder_parent
                                    FOREIGN KEY (parent_folder_id)
                                        REFERENCES homelab_folder(id)
                                        ON DELETE CASCADE
);

CREATE TABLE homelab_file (
                              id UUID PRIMARY KEY,
                              name VARCHAR(255) NOT NULL,
                              owner_id UUID NOT NULL,
                              parent_folder_id UUID,
                              created_at TIMESTAMP,
                              size_in_bytes BIGINT NOT NULL,
                              mime_type VARCHAR(255),
                              physical_path TEXT NOT NULL,

                              CONSTRAINT fk_file_owner
                                  FOREIGN KEY (owner_id)
                                      REFERENCES homelab_user(id)
                                      ON DELETE CASCADE,

                              CONSTRAINT fk_file_folder
                                  FOREIGN KEY (parent_folder_id)
                                      REFERENCES homelab_folder(id)
                                      ON DELETE CASCADE
);

CREATE TABLE storage_quota (
                               id UUID PRIMARY KEY,
                               user_id UUID NOT NULL UNIQUE,
                               max_capacity_bytes BIGINT NOT NULL,
                               used_capacity_bytes BIGINT NOT NULL,
                               storage_plan VARCHAR(50) NOT NULL,

                               CONSTRAINT fk_storage_user
                                   FOREIGN KEY (user_id)
                                       REFERENCES homelab_user(id)
                                       ON DELETE CASCADE
);

-- ===========================
-- Indexes
-- ===========================

CREATE INDEX idx_folder_owner
    ON homelab_folder(owner_id);

CREATE INDEX idx_folder_parent
    ON homelab_folder(parent_folder_id);

CREATE INDEX idx_file_owner
    ON homelab_file(owner_id);

CREATE INDEX idx_file_parent
    ON homelab_file(parent_folder_id);

CREATE INDEX idx_storage_user
    ON storage_quota(user_id);

CREATE INDEX idx_user_email
    ON homelab_user(email);

CREATE INDEX idx_file_name
    ON homelab_file(name);

CREATE INDEX idx_file_created
    ON homelab_file(created_at);

CREATE INDEX idx_folder_created
    ON homelab_folder(created_at);