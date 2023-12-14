package supplobang.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import supplobang.repository.CategoryRepository;
import supplobang.services.CategoryService;
import supplobang.dto.CategoryDto;
import supplobang.dto.ProductDto;
import supplobang.entities.Category;
import supplobang.entities.Product;
import supplobang.exceptions.CategoryAlreadyExistsException;
import supplobang.exceptions.CategoryNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(category -> {
                    return category.convertToCategoryDto();
                })
                .collect(Collectors.toList());
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("category with " + id + "not found"));
    }

    @Override
    public Category getCategoryByName(String categoryName){
        return categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new CategoryNotFoundException(categoryName + " not found"));
    }
    
    @Override
    public List<ProductDto> getAllProductByCategory(String categoryName){
        Category category = getCategoryByName(categoryName);
        return category.getProducts().stream()
                .map(product -> {
                    return product.getProductDto();
                })
                .collect(Collectors.toList());
    }

    @Override
    public Category createCategory(CategoryDto categoryDto) {

        String categoryName = categoryDto.getCategoryName();
    
        validateCategoryExists(categoryName);
    
        Category category = new Category();
        category.setCategoryName(categoryName);
    
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long category_id, CategoryDto updatedCategoryInfo) {
        String updatedCategoryName = updatedCategoryInfo.getCategoryName();

        validateCategoryExists(updatedCategoryName);

        Category existingCategory = getCategoryById(category_id);
        existingCategory.setCategoryName(updatedCategoryName);
        return categoryRepository.save(existingCategory);
    }

    @Override
    public String deleteCategoryById(Long category_id) {
        Category category = getCategoryById(category_id);
        String categoryName = category.getCategoryName();
        categoryRepository.deleteById(category_id);
        return categoryName;
    }

    public List<Product> getAllProductInCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.findByCategoryName(categoryDto.getCategoryName())
                .orElseThrow(() -> new CategoryNotFoundException(categoryDto.getCategoryName() + " not found"));
    
        return category.getProducts();
    }

    @Override
    public void validateCategoryExists(String categoryName) {
        if (categoryRepository.findByCategoryName(categoryName).isPresent()) {
            throw new CategoryAlreadyExistsException("Category with name " + categoryName + " already exists");
        }
    }
}
