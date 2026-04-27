package com.homelab.cloud.domain.exceptions;

public class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }
}
