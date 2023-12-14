package supplobang.security.auth.service.impl;

import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import supplobang.repository.UserRepository;
import supplobang.security.auth.service.AuthenticationService;
import supplobang.services.JWTService;
import supplobang.dto.JwtAuthenticationResponse;
import supplobang.dto.RefreshTokenRequest;
import supplobang.dto.SignInRequest;
import supplobang.dto.SignUpRequest;
import supplobang.entities.Cart;
import supplobang.entities.Role;
import supplobang.entities.User;
import supplobang.exceptions.BadCredentialException;
import supplobang.exceptions.EmailAlreadyExistsException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Override
    public User signup(SignUpRequest signUpRequest){
        
        if(userRepository.findByEmail(signUpRequest.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException("Email already exist");
        }

        User user = new User();
        Cart cart = new Cart();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPhoneNumber(signUpRequest.getPhoneNumber());

        user.setStreetName(signUpRequest.getStreetName());
        user.setBlockNumber(signUpRequest.getBlockNumber());
        user.setUnitNumber(signUpRequest.getUnitNumber());
        user.setPostalCode(signUpRequest.getPostalCode());

        user.setCart(cart);

        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public JwtAuthenticationResponse signin(SignInRequest signInRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
        
        var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new BadCredentialException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);

        return jwtAuthenticationResponse;

    }
    
    @Override
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        // System.out.println(jwtService.extractUsername(refreshTokenRequest.getToken()) + " + + username");
        String userEmail = jwtService.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        System.out.println(user);
        
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)){
            var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());

            return jwtAuthenticationResponse;
        }

        return null;
    }

    
}
