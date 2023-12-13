package supplobang.services;

import java.util.List;

import supplobang.dto.ProductDto;

public interface ProductService {
    List<ProductDto> getAllProducts();
    ProductDto getProduct(Long product_id);
    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(Long product_id, ProductDto updatedProductDto);
    String deleteProductById(long product_id);
}