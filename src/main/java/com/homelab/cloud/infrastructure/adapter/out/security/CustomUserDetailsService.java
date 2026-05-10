package com.homelab.cloud.infrastructure.adapter.out.security;

import com.homelab.cloud.application.port.out.UserRepositoryPort;
import com.homelab.cloud.domain.enums.AccessStatus;
import com.homelab.cloud.domain.exceptions.UserBannedException;
import com.homelab.cloud.domain.exceptions.UserNotApprovedException;
import com.homelab.cloud.domain.exceptions.UserRejectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepositoryPort userRepositoryPort;

    /**
     * The method that returns a user for the Spring language; we use
     * `.disable` to identify whether a user is approved or not each
     * time a request is made.
     * @param email The email of the user trying to authenticate
     * @return UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        var user = userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // ¡AQUÍ ESTÁ LA MAGIA! Evaluamos el estado exacto usando tu Enum
        switch (user.getStatus()) {
            case PENDING -> throw new UserNotApprovedException(AccessStatus.PENDING.getDescription());
            case REJECTED -> throw new UserRejectedException(AccessStatus.REJECTED.getDescription()); // Usamos la nueva
            case BANNED -> throw new UserBannedException(AccessStatus.BANNED.getDescription());
            case APPROVED -> {} // Pasa libremente
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }


}