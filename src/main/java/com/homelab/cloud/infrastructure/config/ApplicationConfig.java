package com.homelab.cloud.infrastructure.config;


// import ports in
import com.homelab.cloud.application.port.in.*;

// import ports out
import com.homelab.cloud.application.port.out.*;

// import services
import com.homelab.cloud.application.service.AuthService;
import com.homelab.cloud.application.service.GetUsersByStatusService;

// import springframework
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// iport spring security
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
    public IGetUsersByStatusUseCase getPendingUsersUseCase(
            UserRepositoryPort userRepositoryPort) {
        return new GetUsersByStatusService(userRepositoryPort);
    }

    @Bean
    public IUpdateUserStatusUseCase updateUserStatusUseCase(
            UserRepositoryPort userRepositoryPort,
            EventPublisherPort eventPublisherPort) { // <--- Agregamos el puerto de eventos
        return new com.homelab.cloud.application.service.UpdateUserStatusService(userRepositoryPort, eventPublisherPort);
    }

    @Bean
    public IUpdateUserByAdminUseCase updateUserByAdminUseCase(
            UserRepositoryPort userRepositoryPort,
            PasswordEncodePort passwordEncodePort
    ) {
        return new com.homelab.cloud.application.service.UpdateUserByAdminService(
                userRepositoryPort,
                passwordEncodePort
        );
    }

    @Bean
    public IDeleteUserUseCase deleteUserUseCase(UserRepositoryPort userRepositoryPort) {
        return new com.homelab.cloud.application.service.DeleteUserService(userRepositoryPort);
    }

    @Bean
    public IInitializeStorageQuotaUseCase initializeStorageQuotaUseCase(
            StorageQuotaRepositoryPort storageQuotaRepositoryPort) {
        return new com.homelab.cloud.application.service.InitializeStorageQuotaService(storageQuotaRepositoryPort);
    }


}
