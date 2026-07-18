package com.homelab.cloud.application.port.in;

import java.util.UUID;

public interface IDeleteUserUseCase {

    void sofDelete (UUID userId);

    void hardDelete (UUID userId);
}
