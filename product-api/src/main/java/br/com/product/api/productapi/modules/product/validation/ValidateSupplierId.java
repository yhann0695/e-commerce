package br.com.product.api.productapi.modules.product.validation;

import br.com.product.api.productapi.modules.product.dto.ProductRequest;
import br.com.product.api.productapi.modules.supplier.service.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ValidateSupplierId implements Validation {

    private SupplierService service;

    @Override
    public void validate(ProductRequest request) {
        service.findById(request.getSupplierId());
    }
}
