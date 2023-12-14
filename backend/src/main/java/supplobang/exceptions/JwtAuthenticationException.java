package supplobang.exceptions;

public class JwtAuthenticationException extends RuntimeException {

    public JwtAuthenticationException(String message) {
        super(message +"this is an error");
    }

    public JwtAuthenticationException(String message, Throwable cause) {
        super(message + "this is an error", cause);
    }
}
