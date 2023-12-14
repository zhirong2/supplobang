package supplobang.controller.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;
import supplobang.dto.ProductDto;
import supplobang.exceptions.ProductNotFoundException;
import supplobang.services.ProductService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {
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
    

    // @GetMapping("/{productId}/flavours")
    // public ResponseEntity<?> getAllFlavoursInProduct(@PathVariable Long productId) {
    //     try {
    //         return ResponseEntity.ok(productService.getAllFlavourInProduct(productId));
    //     } catch (ProductNotFoundException e) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    //     }
    // }

    @PostMapping()
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto) {
        try {
            return ResponseEntity.ok(productService.createProduct(productDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody ProductDto updatedProductDto) {
        try {
            return ResponseEntity.ok(productService.updateProduct(productId, updatedProductDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        try {
            String productName = productService.deleteProductById(productId);
            return ResponseEntity.ok(productName + " has been deleted");
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}


