package com.homelab.cloud.presentation.controller;

// import Use Case
import com.homelab.cloud.application.port.in.AuthUseCase;

// import Dtos
import com.homelab.cloud.presentation.dto.authdto.RegisterRequest;
import com.homelab.cloud.presentation.dto.authdto.LoginRequest;
import com.homelab.cloud.presentation.dto.authdto.AuthResponse;

// import lombok
import lombok.RequiredArgsConstructor;

// import spring framework
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

// import jakarta validation
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest request) {
        // Llamamos al caso de uso. Como request es un 'record', usamos métodos como email() en vez de getEmail()
        authUseCase.requestAccess(request.email(), request.password(), request.nickname());

        // Devolvemos un código 201 (Created) sin cuerpo, indicando que se creó con éxito
        return ResponseEntity.status(HttpStatus.CREATED).build();
        }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // Llamamos al caso de uso, que nos devolverá el JWT si todo sale bien
        String token = authUseCase.login(request.email(), request.password());

        // Empaquetamos el token en nuestro DTO de respuesta y devolvemos un 200 (OK)
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
