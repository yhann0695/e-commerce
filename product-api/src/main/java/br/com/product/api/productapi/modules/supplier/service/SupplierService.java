package br.com.product.api.productapi.modules.supplier.service;

import br.com.product.api.productapi.configuration.exception.ValidationException;
import br.com.product.api.productapi.modules.supplier.dto.SupplierRequest;
import br.com.product.api.productapi.modules.supplier.dto.SupplierResponse;
import br.com.product.api.productapi.modules.supplier.model.Supplier;
import br.com.product.api.productapi.modules.supplier.repository.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@AllArgsConstructor
public class SupplierService {

    private SupplierRepository supplierRepository;

    public Supplier findById(Integer id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ValidationException("There's no supplier for the given ID."));
    }

    public SupplierResponse create(SupplierRequest request) {
        validateSupplierNameInformed(request.getName());
        var entity = supplierRepository.save(Supplier.of(request));
        return SupplierResponse.of(entity);
    }

    private void validateSupplierNameInformed(String name) {
        if(isEmpty(name)) {
            throw new ValidationException("The supplier's name was not informed.");
        }
    }
}
