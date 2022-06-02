package br.com.product.api.productapi.modules.category.service;

import br.com.product.api.productapi.configuration.exception.ValidationException;
import br.com.product.api.productapi.modules.category.dto.CategoryRequest;
import br.com.product.api.productapi.modules.category.dto.CategoryResponse;
import br.com.product.api.productapi.modules.category.model.Category;
import br.com.product.api.productapi.modules.category.repository.CategoryRepository;
import br.com.product.api.productapi.modules.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;
    private ProductService productService;

    public Category findById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ValidationException("There's no category for the given ID."));
    }

    public CategoryResponse findByIdResponse(Integer id) {
        return CategoryResponse.of(findById(id));
    }

    public CategoryResponse create(CategoryRequest request) {
        validateCategoryNameInformed(request);
        var entity= categoryRepository.save(Category.of(request));
        return CategoryResponse.of(entity);
    }

    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream().map(CategoryResponse::of).collect(Collectors.toList());
    }

    public List<CategoryResponse> findByDescription(String description) {
        if(isEmpty(description)) {
            throw new ValidationException("The category description must be informed.");
        }
        return categoryRepository.findByDescriptionIgnoreCaseContaining(description).stream().map(CategoryResponse::of).collect(Collectors.toList());
    }

    private void validateCategoryNameInformed(CategoryRequest request) {
        if(isEmpty(request.getDescription())) {
            throw new ValidationException("The category description was not informed.");
        }
    }



}
