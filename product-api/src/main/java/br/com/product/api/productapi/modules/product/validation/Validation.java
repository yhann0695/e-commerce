package br.com.product.api.productapi.modules.product.validation;

import br.com.product.api.productapi.modules.product.dto.ProductRequest;

public interface Validation {

    void validate(ProductRequest request);
}
