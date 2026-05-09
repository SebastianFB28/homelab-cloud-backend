package com.homelab.cloud.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        // Le preguntamos al Guardia: "¿Quién es el usuario que acaba de entrar?"
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName(); // Esto trae el email del token

        String mensaje = "¡Hola Mundo! El guardia te dejó entrar al Club. Tu correo verificado es: " + userEmail;

        return ResponseEntity.ok(mensaje);
    }
}