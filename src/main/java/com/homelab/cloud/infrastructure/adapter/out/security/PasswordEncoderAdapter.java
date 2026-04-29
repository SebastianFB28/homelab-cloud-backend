package com.homelab.cloud.infrastructure.adapter.out.security;

// import out PasswordEncoderPort
import com.homelab.cloud.application.port.out.PasswordEncodePort;

// import the spring security
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// import lombok
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PasswordEncoderAdapter implements PasswordEncodePort {


    private final PasswordEncoder passwordEncoder;


   @Override
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }


}
