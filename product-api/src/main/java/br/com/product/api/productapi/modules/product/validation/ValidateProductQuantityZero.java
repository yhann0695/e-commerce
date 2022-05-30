package br.com.product.api.productapi.modules.product.validation;

import br.com.product.api.productapi.configuration.exception.ValidationException;
import br.com.product.api.productapi.modules.product.dto.ProductRequest;
import org.springframework.stereotype.Component;

@Component
public class ValidateProductQuantityZero implements Validation {

    private static final Integer ZERO = 0;

    @Override
    public void validate(ProductRequest request) {
        if(request.getQuantityAvailable() <= ZERO) {
            throw new ValidationException("the quantity should not be less or equal to zero.");
        }
    }
}
