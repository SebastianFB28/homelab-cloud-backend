package com.homelab.cloud.application.port.out;

import com.homelab.cloud.domain.model.User;

public interface JwtTokenPort {

    String generateToken(User user);

}
