package supplobang.services;

import java.util.List;

import supplobang.dto.CategoryDto;
import supplobang.dto.ProductDto;
import supplobang.entities.Category;

public interface CategoryService {
    
    List<CategoryDto> getAllCategory();
    Category getCategoryById(Long category_id);
    Category getCategoryByName(String categoryName);
    List<ProductDto> getAllProductByCategory(String categoryName);

    Category createCategory(CategoryDto categoryRequest);

    Category updateCategory(Long category_id, CategoryDto updatedCategoryInfo);

    String deleteCategoryById(Long category_id);

    void validateCategoryExists(String categoryName);
}
