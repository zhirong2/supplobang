package supplobang.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemDto {
    
    @NotNull
    Long product_id;

    @NotNull
    Long flavour_id;

    @Min(value = 1)
    int quantity;
}
