package supplobang.security.auth.service;

import supplobang.dto.JwtAuthenticationResponse;
import supplobang.dto.RefreshTokenRequest;
import supplobang.dto.SignInRequest;
import supplobang.dto.SignUpRequest;
import supplobang.entities.User;

public interface AuthenticationService {
    User signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signin(SignInRequest signInRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
