package supplobang.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignInRequest {
    
    @NotNull(message = "email should not be null")
    @Email
    private String email;

    private String password;
}
