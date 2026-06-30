package com.homelab.cloud.infrastructure.adapter.out.postgres.repository;

import com.homelab.cloud.domain.enums.AccessStatus;
import com.homelab.cloud.infrastructure.adapter.out.postgres.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataUserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    List<UserEntity> findByAccessStatus(AccessStatus accessStatus);


}
