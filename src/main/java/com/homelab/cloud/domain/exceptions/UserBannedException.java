package com.homelab.cloud.domain.exceptions;

public class UserBannedException extends UserException {

    public UserBannedException() {
        super("Tu cuenta ha sido bloqueada");
    }

    public UserBannedException(String message) {
        super(message);
    }
}
