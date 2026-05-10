package com.homelab.cloud.presentation.exception;

import com.homelab.cloud.domain.exceptions.InvalidCredentialsException;
import com.homelab.cloud.domain.exceptions.UserAlreadyExistsException;
import com.homelab.cloud.domain.exceptions.UserBannedException;
import com.homelab.cloud.domain.exceptions.UserNotApprovedException;
import com.homelab.cloud.domain.exceptions.UserRejectedException; // <-- Agregado
import com.homelab.cloud.presentation.dto.AuthDto.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException; // <-- Agregado para validar "a" o "12"
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.CONFLICT.value(), // 409 Conflict
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value(), // 401 Unauthorized
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    // <-- AQUÍ AGREGAMOS UserRejectedException.class -->
    @ExceptionHandler({UserNotApprovedException.class, UserBannedException.class, UserRejectedException.class})
    public ResponseEntity<ErrorResponse> handleAccessDenied(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.FORBIDDEN.value(), // 403 Forbidden
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleSpringSecurityAccessDenied(AccessDeniedException ex) {
        ErrorResponse error = new ErrorResponse(
                "Acceso denegado: No tienes los permisos necesarios para realizar esta acción.",
                HttpStatus.FORBIDDEN.value(), // 403 Forbidden
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    // --- MANEJO DE TOKENS (Expirados o Falsos) ---

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredToken(ExpiredJwtException ex) {
        ErrorResponse error = new ErrorResponse(
                "TOKEN_EXPIRED: Tu sesión ha expirado. Por favor, inicia sesión de nuevo.",
                HttpStatus.UNAUTHORIZED.value(), // 401 Unauthorized
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleInvalidToken(JwtException ex) {
        ErrorResponse error = new ErrorResponse(
                "TOKEN_INVALID: El token proporcionado no es válido o ha sido alterado.",
                HttpStatus.UNAUTHORIZED.value(), // 401 Unauthorized
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    // --- MANEJO DE VALIDACIONES DE DTO (@Valid) ---

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Extrae el mensaje de error del @NotBlank o @Size que falló en el DTO
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

        ErrorResponse error = new ErrorResponse(
                errorMessage,
                HttpStatus.BAD_REQUEST.value(), // 400 Bad Request
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // --- MANEJO GENÉRICO DE ERRORES (El 500) ---

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                "Ocurrió un error interno en el servidor",
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500 Internal Server Error
                LocalDateTime.now()
        );
        // Tip: usa un logger aquí en el futuro -> log.error("Unhandled exception: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}