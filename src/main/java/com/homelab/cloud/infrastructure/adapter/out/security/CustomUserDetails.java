package com.homelab.cloud.infrastructure.adapter.out.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// Lombok generará los getters (getId(), getEmail(), etc.) y el constructor
@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UUID id;            // ¡Lo que nos faltaba!
    private final String email;
    private final String password;
    private final String nickname;
    private final Collection<? extends GrantedAuthority> authorities;

    // Métodos obligatorios de Spring Security:

    @Override
    public String getUsername() {
        return email; // Para Spring, el 'username' es nuestro email
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

}