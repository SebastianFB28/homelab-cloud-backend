package com.homelab.cloud.infrastructure.config;

import com.homelab.cloud.infrastructure.adapter.in.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter; // IMPORTANTE: Inyectamos nuestro guardia

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Mantenemos TU configuración CORS intacta
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 2. Desactivamos CSRF porque nuestra API es Stateless y usa JWT
                .csrf(AbstractHttpConfigurer::disable)

                // 3. Le decimos que NO guarde sesiones en memoria (vital para JWT)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 4. Configuramos quién entra y quién no
                .authorizeHttpRequests(auth -> auth
                        // La Taquilla (Pública)
                        .requestMatchers("/api/auth/**").permitAll()
                        // El Club (Protegido)
                        .anyRequest().authenticated()
                )
                // 5. IMPORTANTE: Ponemos a nuestro guardia ANTES del filtro por defecto de Spring
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // ⚠️ IMPORTANTE: Pon aquí el puerto exacto de tu frontend (suele ser 5173 o 3000)
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));

        // Permitimos los métodos HTTP que vas a usar
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Permitimos todos los headers
        configuration.setAllowedHeaders(List.of("*"));

        // Vital porque en Axios pusiste withCredentials: true
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Aplicamos estas reglas a todas las rutas (/**)
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}