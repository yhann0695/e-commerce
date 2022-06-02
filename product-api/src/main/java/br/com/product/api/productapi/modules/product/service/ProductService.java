package br.com.product.api.productapi.modules.product.service;

import br.com.product.api.productapi.configuration.exception.ValidationException;
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
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

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

    private void validate(ProductRequest request) {
        this.validations.forEach(v -> v.validate(request));
    }

    private Supplier getSupplier(ProductRequest request) {
        return supplierService.findById(request.getSupplierId());
    }

    private Category getCategory(ProductRequest request) {
        return categoryService.findById(request.getCategoryId());
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream().map(ProductResponse::of).collect(Collectors.toList());
    }

    public List<ProductResponse> findByName(String name) {
        if(isEmpty(name)) {
            throw new ValidationException("The product name must be informed.");
        }
        return productRepository.findByNameIgnoreCaseContaining(name).stream().map(ProductResponse::of).collect(Collectors.toList());
    }

    public List<ProductResponse> findBySupplierId(Integer supplierId) {
        if(isEmpty(supplierId)) {
            throw new ValidationException("The product supplier ID name must be informed.");
        }
        return productRepository.findBySupplierId(supplierId).stream().map(ProductResponse::of).collect(Collectors.toList());
    }

    public ProductResponse findByIdResponse(Integer id) {
        return ProductResponse.of(findById(id));
    }

    private Product findById(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new ValidationException("There's no product for the given ID."));
    }

    public List<ProductResponse> findByCategoryId(Integer categoryId) {
        if(isEmpty(categoryId)) {
            throw new ValidationException("The product category ID name must be informed.");
        }
        return productRepository.findByCategoryId(categoryId).stream().map(ProductResponse::of).collect(Collectors.toList());
    }

    public boolean existsByCategoryId(Integer categoryId) {
        return productRepository.existsByCategoryId(categoryId);
    }

    public boolean existsBySupplierId(Integer supplierId) {
        return productRepository.existsBySupplierId(supplierId);
    }

}
