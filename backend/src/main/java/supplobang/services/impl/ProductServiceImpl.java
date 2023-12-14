package supplobang.services.impl;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import supplobang.repository.ProductRepository;
import supplobang.services.BrandService;
import supplobang.services.CategoryService;
import supplobang.services.FlavourService;
import supplobang.services.ProductService;
import supplobang.dto.FlavourDto;
import supplobang.dto.ProductDto;
import supplobang.entities.Brand;
import supplobang.entities.Category;
import supplobang.entities.Flavour;
import supplobang.entities.Product;
import supplobang.exceptions.ProductAlreadyExistsException;
import supplobang.exceptions.ProductNotFoundException;

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
    public List<FlavourDto> getAllFlavourInProduct(Long product_id){
        Product product = getProductById(product_id);
        return product.getFlavours().stream()
                .map(flavour ->  {
                    return flavour.convertToFlavourDto();
                })
                .collect(Collectors.toList());

    }

    @Override
    public List<ProductDto> getAllProductContaining(String productName){
        return productRepository.findAllByProductNameContaining(productName).stream()
                .map(product ->{
                    return product.getProductDto();
                })
                .collect(Collectors.toList());
    }

    @Override
    public Product getProductById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("product with id: " + id + " is not found"));
    }

    public Product getProductByIdOrNull(Long id){
        return productRepository.findById(id)
                .orElse(null);
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
        validateProductExistsByName(productName);
    
        Product product = new Product();
        product.setCategory(category);
        product.setBrand(brand);
        product.setProductName(productName);
        product.setDescription(productDto.getDescription());

    
        productRepository.save(product);

        // Converting FlavourDto to Flavour and associating them with the saved product
        List<Flavour> flavours = flavourService.createFlavours(productDto.getFlavourDtos(), product);
    
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

        flavourService.updateFlavours(updatedProductDto.getFlavourDtos(), existingProduct);

        // Update the existing product with the new information
        existingProduct.setCategory(category);
        existingProduct.setBrand(brand);
        existingProduct.setProductName(updatedProductName);
        existingProduct.setDescription(updatedProductDto.getDescription());
    
        // Save and return the updated product
        existingProduct = productRepository.save(existingProduct);
        return existingProduct.getProductDto();
    }

    public String deleteProductById(long product_id) {
        Product product = getProductById(product_id);
        productRepository.deleteById(product_id);
        return product.getProductName();
    }


    private void validateProductExistsByName(String productName){
        if(productRepository.findByProductName(productName).isPresent()){
            throw new ProductAlreadyExistsException(productName +" already exist");
        }
    }



}
