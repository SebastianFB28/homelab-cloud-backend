package com.homelab.cloud.infrastructure.adapter.out.postgres;

import com.homelab.cloud.application.port.out.UserRepositoryPort;
import com.homelab.cloud.domain.model.User;
import com.homelab.cloud.infrastructure.adapter.out.postgres.entity.UserEntity;
import com.homelab.cloud.infrastructure.adapter.out.postgres.repository.SpringDataUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort{

    private final SpringDataUserRepository jpaRepository;

    @Override
    public User save(User user) {
        // 1. Convertimos el Dominio a Entidad de BD
        UserEntity entityToSave = UserEntity.fromDomain(user);

        // 2. Guardamos en la base de datos usando Spring
        UserEntity savedEntity = jpaRepository.save(entityToSave);

        // 3. Devolvemos el objeto convertido nuevamente a Dominio
        return savedEntity.toDomain();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(UserEntity::toDomain); // Traduce si la caja no está vacía
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
}
