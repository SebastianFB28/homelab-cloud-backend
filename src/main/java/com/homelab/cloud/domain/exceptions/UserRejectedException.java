package com.homelab.cloud.domain.exceptions;

public class UserRejectedException extends UserException{

    public UserRejectedException() {
        super("Tu cuenta ha sido rechazada");
    }

    public UserRejectedException(String message) {
        super(message);
    }
}
