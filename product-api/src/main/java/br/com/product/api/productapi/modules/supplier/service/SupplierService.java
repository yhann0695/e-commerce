package br.com.product.api.productapi.modules.supplier.service;

import br.com.product.api.productapi.configuration.exception.SuccessResponse;
import br.com.product.api.productapi.configuration.exception.ValidationException;
import br.com.product.api.productapi.modules.product.service.ProductService;
import br.com.product.api.productapi.modules.supplier.dto.SupplierRequest;
import br.com.product.api.productapi.modules.supplier.dto.SupplierResponse;
import br.com.product.api.productapi.modules.supplier.model.Supplier;
import br.com.product.api.productapi.modules.supplier.repository.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@AllArgsConstructor
public class SupplierService {

    private SupplierRepository supplierRepository;
    private ProductService productService;

    public Supplier findById(Integer id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ValidationException("There's no supplier for the given ID."));
    }

    public List<SupplierResponse> findAll() {
        return supplierRepository.findAll().stream().map(SupplierResponse::of).collect(Collectors.toList());
    }

    public List<SupplierResponse> findByName(String name) {
        validateInformedData(isEmpty(name), "The supplier's name was not informed.");
        return supplierRepository.findByNameIgnoreCaseContaining(name).stream().map(SupplierResponse::of).collect(Collectors.toList());
    }

    public SupplierResponse create(SupplierRequest request) {
        validateInformedData(isEmpty(request.getName()), "The supplier's name was not informed.");
        var entity = supplierRepository.save(Supplier.of(request));
        return SupplierResponse.of(entity);
    }

    public SupplierResponse findByIdResponse(Integer id) {
        return SupplierResponse.of(findById(id));
    }

    public SuccessResponse delete(Integer id) {
        validateInformedData(isEmpty(id), "The supplier's ID must be informed.");
        validateInformedData(productService.existsBySupplierId(id), "You cannot delete this supplier because it's already defined by a product");
        supplierRepository.deleteById(id);
        return SuccessResponse.create("The supplier  was deleted.");
    }

    private void validateInformedData(boolean data, String msg) {
        if(data) {
            throw new ValidationException(msg);
        }
    }


}
