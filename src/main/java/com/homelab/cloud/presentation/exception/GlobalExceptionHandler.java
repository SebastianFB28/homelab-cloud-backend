package com.homelab.cloud.presentation.exception;

import com.homelab.cloud.domain.exceptions.InvalidCredentialsException;
import com.homelab.cloud.domain.exceptions.UserAlreadyExistsException;
import com.homelab.cloud.domain.exceptions.UserBannedException;
import com.homelab.cloud.domain.exceptions.UserNotApprovedException;
import com.homelab.cloud.presentation.dto.AuthDto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler({UserNotApprovedException.class, UserBannedException.class})
    public ResponseEntity<ErrorResponse> handleAccessDenied(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.FORBIDDEN.value(), // 403 Forbidden (Sabemos quién eres, pero no puedes pasar)
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                "Ocurrió un error interno en el servidor",
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500 Internal Server Error
                LocalDateTime.now()
        );
        // Opcional: Aquí podrías hacer un log.error(ex.getMessage()) para ver qué falló en la consola
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
