package br.com.product.api.productapi.modules.category.service;

import br.com.product.api.productapi.configuration.exception.SuccessResponse;
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

    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream().map(CategoryResponse::of).collect(Collectors.toList());
    }

    public CategoryResponse create(CategoryRequest request) {
        validateInformedData(isEmpty(request.getDescription()), "The category description was not informed.");
        var entity= categoryRepository.save(Category.of(request));
        return CategoryResponse.of(entity);
    }

    public List<CategoryResponse> findByDescription(String description) {
        validateInformedData(isEmpty(description), "The category description must be informed.");
        return categoryRepository.findByDescriptionIgnoreCaseContaining(description).stream().map(CategoryResponse::of).collect(Collectors.toList());
    }

    public SuccessResponse delete(Integer id) {
        validateInformedData(isEmpty(id), "The supplier's ID must be informed.");
        validateInformedData(productService.existsByCategoryId(id), "You cannot delete this category because it's already defined by a product");
        categoryRepository.deleteById(id);
        return SuccessResponse.create("The category  was deleted.");
    }

    private void validateInformedData(boolean data, String msg) {
        if(data) {
            throw new ValidationException(msg);
        }
    }

}
