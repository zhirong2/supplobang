package supplobang.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import supplobang.dto.ProductDto;
import supplobang.exceptions.ProductAlreadyExistsException;
import supplobang.exceptions.ProductNotFoundException;
import supplobang.services.ProductService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;




@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<?> getProduct(@PathVariable("product_id") Long productId) {
        try {
            return ResponseEntity.ok(productService.getProduct(productId));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/admin")
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto) {
        try {
            return ResponseEntity.ok(productService.createProduct(productDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/admin/{product_id}")
    public ResponseEntity<?> updateProduct(@PathVariable("product_id") Long product_id, @RequestBody ProductDto updatedProductDto) {
        try {
            return ResponseEntity.ok(productService.updateProduct(product_id, updatedProductDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/admin/{product_id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("product_id") Long product_id) {
        try {
            String productName = productService.deleteProductById(product_id);
            return ResponseEntity.ok(productName + " has been deleted");
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

