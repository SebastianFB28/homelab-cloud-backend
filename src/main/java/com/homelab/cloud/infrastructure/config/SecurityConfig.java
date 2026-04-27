package com.homelab.cloud.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Desactivamos CSRF porque nuestra API es Stateless y usa JWT
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Le decimos que NO guarde sesiones en memoria (vital para JWT)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 3. Configuramos quién entra y quién no
                .authorizeHttpRequests(auth -> auth
                        // Dejamos las puertas abiertas de par en par para todo lo que empiece con /api/auth
                        .requestMatchers("/api/auth/**").permitAll()

                        // Cualquier otra puerta de la aplicación (en el futuro) exigirá estar autenticado
                        .anyRequest().authenticated()
                );

        return http.build();
    }


}
