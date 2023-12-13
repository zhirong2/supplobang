package supplobang.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryDto {

    private long id;

    @NotNull(message = "Category cannot be null")
    private String categoryName;
    
    private List<ProductDto> productDtos;
}
