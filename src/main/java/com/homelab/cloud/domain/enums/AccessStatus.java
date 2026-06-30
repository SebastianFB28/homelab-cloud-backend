package com.homelab.cloud.domain.enums;

import lombok.Getter;

@Getter
public enum AccessStatus {

    PENDING("Estamos revisando tu solicitud de acceso"),
    APPROVED("Tu solicitud de acceso ha sido aprobada"),
    REJECTED("Tu solicitud de acceso ha sido rechazada"),
    BANNED("Tu acceso ha sido bloqueado por acciones inapropiadas");

    private final String description;

    AccessStatus(String description) {
        this.description = description;
    }
}
