package com.homelab.cloud.domain.exceptions;

public class UserAlreadyExistsException  extends UserException {
    public UserAlreadyExistsException(String message) {
        super("El gmail ya esta registrado: " + message);
    }
}
