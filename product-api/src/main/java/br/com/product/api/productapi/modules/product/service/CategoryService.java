package br.com.product.api.productapi.modules.product.service;

import br.com.product.api.productapi.configuration.exception.ValidationException;
import br.com.product.api.productapi.modules.product.dto.category.CategoryRequest;
import br.com.product.api.productapi.modules.product.dto.category.CategoryResponse;
import br.com.product.api.productapi.modules.product.model.Category;
import br.com.product.api.productapi.modules.product.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;

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
