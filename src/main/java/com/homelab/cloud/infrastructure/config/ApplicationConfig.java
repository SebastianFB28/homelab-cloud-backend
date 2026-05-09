package com.homelab.cloud.infrastructure.config;


// import ports in
import com.homelab.cloud.application.port.in.AuthUseCase;
import com.homelab.cloud.application.port.in.GetPendingUsersUseCase;

// import ports out
import com.homelab.cloud.application.port.out.PasswordEncodePort;
import com.homelab.cloud.application.port.out.UserRepositoryPort;

// import services
import com.homelab.cloud.application.service.AuthService;
import com.homelab.cloud.application.service.AdminGetPendingUserService;

// import springframework
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// iport spring security
import com.homelab.cloud.application.port.out.JwtTokenPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

    // Spring busca los métodos con @Bean.
    // Al ver los parámetros, busca en su "cajita" las implementaciones de esos puertos
    // y se los inyecta automáticamente.
    @Bean
    public AuthUseCase authUseCase(
            UserRepositoryPort userRepositoryPort,
            PasswordEncodePort passwordEncodePort,
            JwtTokenPort jwtTokenPort
    ) {
        // Nosotros instanciamos la clase de Java puro y se la devolvemos a Spring
        return new AuthService(userRepositoryPort, jwtTokenPort, passwordEncodePort);
    }

    /**
     * Bean for the password encoder
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public GetPendingUsersUseCase getPendingUsersUseCase(
            UserRepositoryPort userRepositoryPort) {
        return new AdminGetPendingUserService(userRepositoryPort);
    }

}
