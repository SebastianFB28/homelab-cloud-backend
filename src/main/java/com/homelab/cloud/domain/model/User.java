package com.homelab.cloud.domain.model;

// import the necessary dependencies from Lombok
import com.homelab.cloud.domain.exceptions.UserBannedException;
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


    /**
     * Quick status change for a newly created user
     * @param newStatus
     */
    public void changeAccessStatus(AccessStatus newStatus) {

        // Implement the logic to prevent changing status from BANNED to PENDING
        if (this.status == AccessStatus.BANNED ){
            throw new UserBannedException("Usuario baneado no puede ser cambiado a pendiente");
        }
        this.status = newStatus;
    }

    /**
     * We take the new status and verify that it is not the same
     * as the one the user already has in the system.
     * @param status
     * @return
     */
    public boolean validateStatus(AccessStatus status) {
        return this.status == status;
    }

    /**
     * We validate whether the event can be executed or not.
     * @param newStatus
     * @return
     */
    public boolean wasApprovedEvent(AccessStatus newStatus) {
        return this.status != AccessStatus.APPROVED
                && newStatus == AccessStatus.APPROVED;
    }

    /**
     * A more comprehensive change, for a specific user or banned users.
     * It validates the fields and only changes the fields requested to be changed.
     * @param newNickname
     * @param newEncodedPassword
     * @param newRole
     * @param newStatus
     */
    public void updateByAdmin(String newNickname, String newEncodedPassword, Role newRole, AccessStatus newStatus) {
        if (newNickname != null && !newNickname.isBlank()) {
            this.nickname = newNickname;
        }


        if (newEncodedPassword != null) {
            this.password = newEncodedPassword;
        }

        if (newRole != null) {
            this.role = newRole;
        }
        if (newStatus != null) {
            this.status = newStatus;
        }
    }

    /**
     * Change the user's status to "deleted."
     * @param status
     */
    public void sofDelete (){
            this.status = AccessStatus.DELETED;

    }


    /**
     * User method to update nickname or password
     * @param newNickname
     * @param newPassword
     */
    public void updateProfile (String newNickname , String newPassword){

        if (newNickname != null && !newNickname.isBlank()) {
            this.nickname = newNickname;
        }
        if (newPassword != null && !newPassword.isBlank()) {
            this.password = newPassword;
        }
    }

}
