package br.com.product.api.productapi.modules.product.service;

import br.com.product.api.productapi.modules.product.dto.category.CategoryRequest;
import br.com.product.api.productapi.modules.product.dto.category.CategoryResponse;
import br.com.product.api.productapi.modules.product.model.Category;
import br.com.product.api.productapi.modules.product.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryResponse create(CategoryRequest request) {
        var entity= categoryRepository.save(Category.of(request));
        return CategoryResponse.of(entity);
    }
}
