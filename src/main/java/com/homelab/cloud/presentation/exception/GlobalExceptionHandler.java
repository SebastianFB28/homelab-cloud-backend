package com.homelab.cloud.presentation.exception;

import com.homelab.cloud.domain.exceptions.*;
import com.homelab.cloud.presentation.dto.authdto.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException; // <-- Agregado para validar "a" o "12"
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@Slf4j
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
        // 3. Imprime la traza completa en los logs de Docker
        log.error("⚠️ Unhandled exception caught in GlobalExceptionHandler:", ex);

        // 4. Te devuelve el mensaje detallado para no andar a ciegas en desarrollo
        String message = (ex.getMessage() != null) ? ex.getMessage() : "Ocurrió un error interno en el servidor";

        ErrorResponse error = new ErrorResponse(
                message,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(), // 404 Not Found
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        ErrorResponse error = new ErrorResponse(
                "Error en el formato de los datos enviados. Verifica que los valores ingresados (como el rol o el estado) sean válidos.",
                HttpStatus.BAD_REQUEST.value(), // 400 Bad Request
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }



    // 2. ESCUDO PARA LA URL (Path Variables y Query Params)
    // Atrapa cuando envías /users?status=BATMAN o /users/un-uuid-falso
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        // Extraemos el nombre del parámetro que falló para darle un mejor mensaje al cliente
        String paramName = ex.getName();

        ErrorResponse error = new ErrorResponse(
                "El valor enviado para el parámetro '" + paramName + "' no es válido o tiene un formato incorrecto.",
                HttpStatus.BAD_REQUEST.value(), // 400 Bad Request
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}