package supplobang.controller;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import supplobang.dto.CategoryDto;
import supplobang.exceptions.CategoryAlreadyExistsException;
import supplobang.exceptions.CategoryNotFoundException;
import supplobang.services.CategoryService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    @PostMapping("/admin")
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto){
        try{
            return ResponseEntity.ok(categoryService.createCategory(categoryDto));
        } catch (CategoryAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("admin/{category_id}")
    public ResponseEntity<?> updateCategory(@PathVariable("category_id") Long category_id, @RequestBody CategoryDto categoryDto) {
        try {
            return ResponseEntity.ok(categoryService.updateCategory(category_id, categoryDto));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("admin/{category_id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("category_id") Long category_id) {
        try {
            String categoryName = categoryService.deleteCategoryById(category_id);
            return ResponseEntity.ok(categoryName + " has been deleted.");
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}