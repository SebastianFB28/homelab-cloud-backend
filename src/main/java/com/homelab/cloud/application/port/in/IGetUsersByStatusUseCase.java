package com.homelab.cloud.application.port.in;

// import domain model user
import com.homelab.cloud.domain.enums.AccessStatus;
import com.homelab.cloud.domain.model.User;
import java.util.List;

public interface IGetUsersByStatusUseCase {

    /**
     * Get all users by status
     * @return List<User>
     */
    List<User> getUserByStatus(AccessStatus status);
}
