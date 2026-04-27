package com.homelab.cloud.infrastructure.adapter.out.postgres.entity;

// import domain model user
import com.homelab.cloud.domain.model.User;
import com.homelab.cloud.domain.enums.AccessStatus;
import com.homelab.cloud.domain.enums.Role;

// import jakarta and lombok
import jakarta.persistence.*;
import lombok.*;

// import java util
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "homelab_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    private UUID id;

    @Column(unique = true , nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private AccessStatus accessStatus;

    private String nickname;

    private LocalDateTime createdAt;

    // Mappers for translating between layers
    public static UserEntity fromDomain(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .accessStatus(user.getStatus())
                .nickname(user.getNickname())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public User toDomain() {
        return User.builder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
                .role(this.role)
                .status(this.accessStatus)
                .nickname(this.nickname)
                .createdAt(this.createdAt)
                .build();
    }


}
