package br.com.product.api.productapi.modules.product.validation;

import br.com.product.api.productapi.modules.category.service.CategoryService;
import br.com.product.api.productapi.modules.product.dto.ProductRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ValidateCategoryId implements Validation {

    private CategoryService service;

    @Override
    public void validate(ProductRequest request) {
        service.findById(request.getCategoryId());
    }
}
