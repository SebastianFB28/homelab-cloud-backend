package com.homelab.cloud.domain.model;

// import the necessary dependencies from Lombok
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

// import UUID for unique identifier and LocalDateTime for timestamp
import java.time.LocalDateTime;
import java.util.UUID;

// import the enum
import com.homelab.cloud.domain.enums.Role;
import com.homelab.cloud.domain.enums.AccessStatus;

// annotate the class with Lombok annotations to generate getters, setters, constructors, and builder pattern
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private UUID id; // Unique identifier for the user
    private String email;
    private String password;
    private Role role;
    private AccessStatus status;
    private String nickname;
    private LocalDateTime createdAt;

}
