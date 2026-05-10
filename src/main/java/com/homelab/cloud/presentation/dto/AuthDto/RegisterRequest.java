package com.homelab.cloud.presentation.dto.AuthDto;

/**
 *  RegisterRequest DTO
 *  Data entry form for registering a person
 */
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "El nickname no puede estar vacío ni contener solo espacios")
        @Size(min = 3, max = 20, message = "El nickname debe tener entre 3 y 20 caracteres")
        String nickname,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El formato del correo no es válido (ejemplo@dominio.com)")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres por seguridad")
        String password
) {
}
