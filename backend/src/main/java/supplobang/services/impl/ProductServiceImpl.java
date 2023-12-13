package supplobang.services.impl;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import supplobang.dto.ProductDto;
import supplobang.entities.Brand;
import supplobang.entities.Category;
import supplobang.entities.Flavour;
import supplobang.entities.Product;
import supplobang.exceptions.ProductAlreadyExistsException;
import supplobang.exceptions.ProductNotFoundException;
import supplobang.repository.ProductRepository;
import supplobang.services.BrandService;
import supplobang.services.CategoryService;
import supplobang.services.FlavourService;
import supplobang.services.ProductService;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BrandService brandService;
    private final CategoryService categoryService;
    private final FlavourService flavourService;

    @Override
    public List<ProductDto> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> {
                    return product.getProductDto();
                })
                .collect(Collectors.toList());
    }

    public ProductDto getProduct(Long product_id){
        Product product = getProductById(product_id);
        return product.getProductDto();
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        String categoryName = productDto.getCategory();
        String brandName = productDto.getBrand();
        String productName = productDto.getProductName();
    
        // Validate category and brand
        Category category = categoryService.getCategoryByName(categoryName);
        Brand brand = brandService.getBrandByName(brandName);
    
        // Check if product exists
        validateProductExists(productName);
    
        Product product = new Product();
        product.setCategory(category);
        product.setBrand(brand);
        product.setProductName(productName);
        product.setDescription(productDto.getDescription());
        product.setTotalQuantity(0);
    
        productRepository.save(product);

        // Converting FlavourDto to Flavour and associating them with the saved product
        List<Flavour> flavours = flavourService.createFlavours(productDto.getFlavourDtos(), product);
    
        // Get the total quantity
        int totalQuantity = flavours.stream()
                .mapToInt(Flavour::getFlavourQuantity)
                .sum();
    
        // Set total quantity and flavours for the product
        product.setTotalQuantity(totalQuantity);
        product.setFlavours(flavours);
    
        // Save the updated product with associated flavours
        return productRepository.save(product).getProductDto();
    }
    

    @Override
    public ProductDto updateProduct(Long product_id, ProductDto updatedProductDto){

        //Validate product
        Product existingProduct = getProductById(product_id);
        
        String categoryName = updatedProductDto.getCategory();
        String brandName = updatedProductDto.getBrand();

        //Validate category and brand
        Category category = categoryService.getCategoryByName(categoryName);
        Brand brand = brandService.getBrandByName(brandName);

        //Ensure name is not used
        String updatedProductName = updatedProductDto.getProductName();
        validateProductExists(updatedProductName);

        // update 
        List<Flavour> updatedFlavours = flavourService.updateFlavours(updatedProductDto.getFlavourDtos(), existingProduct);
    
        // Calculate the total quantity from updated flavours
        int updatedTotalQuantity = updatedFlavours.stream()
                .mapToInt(Flavour::getFlavourQuantity)
                .sum();
    
        // Update the existing product with the new information
        existingProduct.setCategory(category);
        existingProduct.setBrand(brand);
        existingProduct.setProductName(updatedProductName);
        existingProduct.setDescription(updatedProductDto.getDescription());
        existingProduct.setTotalQuantity(updatedTotalQuantity);
        existingProduct.setFlavours(updatedFlavours);
    
        // Save and return the updated product
        return productRepository.save(existingProduct).getProductDto();
    }

    public String deleteProductById(long product_id) {
        Product product = getProductById(product_id);
        productRepository.deleteById(product_id);
        return product.getProductName();
    }


    private void validateProductExists(String productName){
        if(productRepository.findByProductName(productName).isPresent()){
            throw new ProductAlreadyExistsException(productName +" already exist");
        }
    }

    private Product getProductById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("product with id: " + id + " is not found"));
    }

}
