package br.com.product.api.productapi.modules.category.service;

import br.com.product.api.productapi.configuration.exception.ValidationException;
import br.com.product.api.productapi.modules.category.dto.CategoryRequest;
import br.com.product.api.productapi.modules.category.dto.CategoryResponse;
import br.com.product.api.productapi.modules.category.model.Category;
import br.com.product.api.productapi.modules.category.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;

    public Category findById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ValidationException("There's no category for the given ID."));
    }

    public CategoryResponse create(CategoryRequest request) {
        validateCategoryNameInformed(request);
        var entity= categoryRepository.save(Category.of(request));
        return CategoryResponse.of(entity);
    }

    private void validateCategoryNameInformed(CategoryRequest request) {
        if(isEmpty(request.getDescription())) {
            throw new ValidationException("The category description was not informed.");
        }
    }
}
