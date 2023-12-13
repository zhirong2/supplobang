package supplobang.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @NotNull(message = "Username should not be null")
    @Size(max = 25)
    private String username;

    @NotNull
    @Pattern(regexp = "^[89]\\d{7}$", message = "Please provide a valid phone number starting with 8 or 9")
    private String phoneNumber;

    @NotNull(message = "StreeName should not be null")
    @Size(max = 25)
    private String streetName;

    @NotNull(message = "BlockNumber should not be null")
    @Size(max = 10)
    private String blockNumber;

    @NotNull(message = "UnitNumber should not be null")
    @Size(max = 10)
    private String unitNumber;

    @NotNull(message = "PostalCode should not be null")
    @Size(min = 6, max = 6)
    private String postalCode;
    
}
