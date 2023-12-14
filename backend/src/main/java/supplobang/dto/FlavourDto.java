package supplobang.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FlavourDto {

    private long id;

    @NotNull(message = "Flavour cannot be null")
    private String flavourName;

    @DecimalMin(value = "0.0", message = "Price must be a positive number")
    private double price;

    @Min(value = 0 , message = "Flavour quantity must be a positive number")
    private int flavourQuantity;

    private Long product_id;
    
}
