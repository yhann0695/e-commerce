package br.com.product.api.productapi.modules.product.service;

import br.com.product.api.productapi.modules.category.model.Category;
import br.com.product.api.productapi.modules.category.service.CategoryService;
import br.com.product.api.productapi.modules.product.dto.ProductRequest;
import br.com.product.api.productapi.modules.product.dto.ProductResponse;
import br.com.product.api.productapi.modules.product.model.Product;
import br.com.product.api.productapi.modules.product.repository.ProductRepository;
import br.com.product.api.productapi.modules.product.validation.Validation;
import br.com.product.api.productapi.modules.supplier.model.Supplier;
import br.com.product.api.productapi.modules.supplier.service.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;
    private SupplierService supplierService;
    private CategoryService categoryService;
    private List<Validation> validations;

    public ProductResponse create(ProductRequest request) {
        validate(request);
        Category category = getCategory(request);
        Supplier supplier = getSupplier(request);
        var product = productRepository.save(Product.of(request, supplier, category));
        return ProductResponse.of(product);
    }

    private Supplier getSupplier(ProductRequest request) {
        return supplierService.findById(request.getSupplierId());
    }

    private Category getCategory(ProductRequest request) {
        return categoryService.findById(request.getCategoryId());
    }

    private void validate(ProductRequest request) {
        this.validations.forEach(v -> v.validate(request));
    }


}
