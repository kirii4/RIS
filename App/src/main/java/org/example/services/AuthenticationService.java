package org.example.services;

import com.nikak.pspkurssecurity.dto.*;
import com.nikak.pspkurssecurity.entities.Role;

public interface AuthenticationService {

    Role getRole(String email);
    JwtAuthenticationResponse signup(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signin(SigninRequest signinRequest);

    JwtAuthenticationResponse changePassword(String email, ChangePasswordRequest changePasswordRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
