package supplobang.services.impl;

import io.jsonwebtoken.JwtException;

public class JwtAuthenticationException extends RuntimeException {

    public JwtAuthenticationException(String message) {
        super(message +"this is an error");
    }

    public JwtAuthenticationException(String message, Throwable cause) {
        super(message + "this is an error", cause);
    }
}
