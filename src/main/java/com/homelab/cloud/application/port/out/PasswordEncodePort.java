package com.homelab.cloud.application.port.out;

public interface PasswordEncodePort {

    String encode(String rawPassword);

    boolean matches(String rawPassword, String encodedPassword);

}
