package supplobang.controller.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;
import supplobang.dto.ProductDto;
import supplobang.exceptions.ProductNotFoundException;
import supplobang.services.ProductService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class UserProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable Long productId) {
        try {
            return ResponseEntity.ok(productService.getProduct(productId));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDto>> getAllProductContaining(@PathVariable String name){
        return ResponseEntity.ok(productService.getAllProductContaining(name));
    }
    
}



