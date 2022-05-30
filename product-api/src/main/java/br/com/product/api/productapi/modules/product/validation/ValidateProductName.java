package br.com.product.api.productapi.modules.product.validation;

import br.com.product.api.productapi.configuration.exception.ValidationException;
import br.com.product.api.productapi.modules.product.dto.ProductRequest;
import org.springframework.stereotype.Component;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class ValidateProductName implements Validation {

    @Override
    public void validate(ProductRequest request) {
        if(isEmpty(request.getName())) {
            throw new ValidationException("The product's name was not informed.");
        }
    }
}
