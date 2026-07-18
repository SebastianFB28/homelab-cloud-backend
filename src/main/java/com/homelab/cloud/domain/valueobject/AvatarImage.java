package com.homelab.cloud.domain.valueobject;

public record AvatarImage (
        byte[] content,  // Cambiado de 'image' a 'content'
        String mimeType  // Corregido de 'mineType' a 'mimeType'
) {
    public static final String DEFAULT_MIME_TYPE = "image/jpeg";
}