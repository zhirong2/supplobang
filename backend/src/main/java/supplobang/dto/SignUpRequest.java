package supplobang.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {
    
    @NotNull(message = "username should not be null")
    @Size(max = 25)
    private String username;

    @NotNull(message = "password should not be null")
    private String password;

    @NotNull
    @Pattern(regexp = "^[89]\\d{7}$", message = "Please provide a valid phone number starting with 8 or 9")
    private String phoneNumber;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(max = 25)
    private String streetName;

    @NotNull
    @Size(max = 10)
    private String blockNumber;

    @NotNull
    @Size(max = 10)
    private String unitNumber;

    @NotNull
    @Size(min = 6, max = 6)
    private String postalCode;
}
