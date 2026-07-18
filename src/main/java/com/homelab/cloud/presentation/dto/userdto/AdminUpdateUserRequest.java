package com.homelab.cloud.presentation.dto.userdto;

import com.homelab.cloud.domain.enums.Role;
import com.homelab.cloud.domain.enums.AccessStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AdminUpdateUserRequest(

        String nickname,

        String password,

        Role role,

        AccessStatus status
) {

}