package supplobang.services;

import java.util.List;

import supplobang.dto.FlavourDto;
import supplobang.dto.ProductDto;
import supplobang.entities.Product;

public interface ProductService {
    List<ProductDto> getAllProducts();
    ProductDto getProduct(Long product_id);
    Product getProductById(Long id);
    Product getProductByIdOrNull(Long id);
    List<ProductDto> getAllProductContaining(String productName);
    List<FlavourDto> getAllFlavourInProduct(Long product_id);

    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(Long product_id, ProductDto updatedProductDto);

    String deleteProductById(long product_id);
}