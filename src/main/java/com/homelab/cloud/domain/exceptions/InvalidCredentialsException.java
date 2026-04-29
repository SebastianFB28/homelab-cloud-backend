package com.homelab.cloud.domain.exceptions;

public class InvalidCredentialsException extends UserException {

    public InvalidCredentialsException() {
        super("usuario o contraseña incorrectos");
    }
}
