package com.homelab.cloud.application.service;

// model user
import com.homelab.cloud.domain.exceptions.*;
import com.homelab.cloud.domain.model.User;

// import emuns the user model
import com.homelab.cloud.domain.enums.Role;
import com.homelab.cloud.domain.enums.AccessStatus;

// import port in AuthUseCase
import com.homelab.cloud.application.port.in.AuthUseCase;

// import ports out
import com.homelab.cloud.application.port.out.UserRepositoryPort;
import com.homelab.cloud.application.port.out.JwtTokenPort;
import com.homelab.cloud.application.port.out.PasswordEncodePort;

// import the exception the user model

// import lombok required for the constructor
import lombok.RequiredArgsConstructor;

// import java util
import java.time.LocalDateTime;
import java.util.UUID;

// create the constructor autoamissing the dependencies
@RequiredArgsConstructor
public class AuthService implements  AuthUseCase{

    // create the dependencies (ports out)
    private final UserRepositoryPort userRepositoryPort;
    private final JwtTokenPort jwtTokenPort;
    private final PasswordEncodePort passwordEncodePort;

    /**
     * validate the user credentials and return the jwt token if the credentials are valid
     * @param email
     * @param password
     * @return jwt token
     */
    @Override
    public String login(String email, String password) {

        // validate if the email account exists
        User user = userRepositoryPort.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        // validate if the password is correct
        if (!passwordEncodePort.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        // validate if the user status is approved
        if (user.getStatus() == AccessStatus.PENDING) {
            throw new UserNotApprovedException();
        }

        // validate if the user is banned
        if (user.getStatus() == AccessStatus.BANNED){
            throw new UserBannedException();
        }

        // validate if the user is rejected
        if (user.getStatus() == AccessStatus.REJECTED){
            throw new UserRejectedException();
        }

        // generate the jwt token
        return jwtTokenPort.generateToken(user);
    }



    /**
     * Create a new user with the given email, password and nickname and return the jwt token if the user is created successfully
     * @param email
     * @param password
     * @param nickname
     */
    @Override
    public void requestAccess(String email, String password, String nickname) {

        // validate if the email acount is already registered
        if (userRepositoryPort.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
        }

        // encrypt the password
        String encryptedPassword = passwordEncodePort.encode(password);

        // create the new user
        User newUser = User.builder()
            .id(UUID.randomUUID())
            .email(email)
            .password(encryptedPassword)
            .nickname(nickname)
            .role(Role.USER)
            .status(AccessStatus.PENDING)
            .createdAt(LocalDateTime.now())
            .build();

        // save the new user
        userRepositoryPort.save(newUser);

    }
}
