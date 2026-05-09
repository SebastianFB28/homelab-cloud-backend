package com.homelab.cloud.application.port.out;

// import User
import com.homelab.cloud.domain.enums.AccessStatus;
import com.homelab.cloud.domain.model.User;

// import java optional
import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {


    void save(User user);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByStatus(AccessStatus status);

}
