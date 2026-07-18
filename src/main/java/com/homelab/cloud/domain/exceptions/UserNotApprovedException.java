package com.homelab.cloud.domain.exceptions;

public class UserNotApprovedException  extends UserException {

    public UserNotApprovedException() {
        super("Tu cuenta no ha sido aprobada");
    }

    public UserNotApprovedException(String message) {
        super(message);
    }
}
