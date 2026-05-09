package com.homelab.cloud.application.port.in;

// import domain model user
import com.homelab.cloud.domain.model.User;
import java.util.List;

public interface GetPendingUsersUseCase {

    /**
     * Get all pending users
     * @return List<User>
     */
    List<User> getPendingUsers();
}
