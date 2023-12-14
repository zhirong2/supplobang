package supplobang.security.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import supplobang.security.auth.service.AuthenticationService;
import supplobang.dto.JwtAuthenticationResponse;
import supplobang.dto.RefreshTokenRequest;
import supplobang.dto.SignInRequest;
import supplobang.dto.SignUpRequest;
import supplobang.exceptions.BadCredentialException;
import supplobang.exceptions.EmailAlreadyExistsException;



@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest signUpRequest){
        try{
            return ResponseEntity.ok(authenticationService.signup(signUpRequest));
        }catch (EmailAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
       
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody SignInRequest signInRequest){
        try{
            return ResponseEntity.ok(authenticationService.signin(signInRequest));
        }catch(BadCredentialException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
  
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }
    
}
