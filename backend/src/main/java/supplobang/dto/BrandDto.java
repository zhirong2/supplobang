package supplobang.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BrandDto {

    private long id;

    @NotNull(message = "Brand cannot be null")
    private String brandName;

    private List<ProductDto> productsDto;
}
