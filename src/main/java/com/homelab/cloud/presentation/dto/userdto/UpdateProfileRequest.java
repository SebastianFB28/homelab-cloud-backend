package com.homelab.cloud.presentation.dto.userdto;

public record UpdateProfileRequest (
        String nickname,
        String password
){
}
