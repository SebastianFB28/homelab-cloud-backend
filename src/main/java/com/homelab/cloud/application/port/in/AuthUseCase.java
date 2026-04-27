package com.homelab.cloud.application.port.in;

public interface AuthUseCase {

    // Retornamos un String que será el JWT Token
    String login(String email, String password);

    // Aquí recibimos los datos para solicitar acceso
    void requestAccess(String email, String password, String nickname);

}
