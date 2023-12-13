package supplobang.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class ProductDto {

    private long id;

    @NotNull(message = "Category cannot be null")
    private String category;

    @NotNull(message = "Brand cannot be null")
    private String brand;

    @NotNull(message = "Product name cannot be null")
    private String productName;

    @NotNull(message = "Description should not be null")
    private String description;

    @Min(value = 0, message = "Total quantity must be a positive number")
    private int totalQuantity;

    @Size(min = 1, message = "At least one flavour must be specified")
    private List<FlavourDto> flavourDtos;
    
}
