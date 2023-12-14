package supplobang.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryDto {

    @NotNull(message = "Category cannot be null")
    private String categoryName;
    
    private List<ProductDto> productDtos;
}
