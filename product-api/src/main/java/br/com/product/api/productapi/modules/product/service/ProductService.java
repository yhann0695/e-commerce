package br.com.product.api.productapi.modules.product.service;

import br.com.product.api.productapi.configuration.exception.SuccessResponse;
import br.com.product.api.productapi.configuration.exception.ValidationException;
import br.com.product.api.productapi.modules.category.model.Category;
import br.com.product.api.productapi.modules.category.service.CategoryService;
import br.com.product.api.productapi.modules.product.dto.*;
import br.com.product.api.productapi.modules.product.model.Product;
import br.com.product.api.productapi.modules.product.repository.ProductRepository;
import br.com.product.api.productapi.modules.product.sales.client.SalesClient;
import br.com.product.api.productapi.modules.product.sales.dto.SalesConfirmationDTO;
import br.com.product.api.productapi.modules.product.sales.enums.SalesStatus;
import br.com.product.api.productapi.modules.product.sales.rabbitmq.SalesConfirmationSender;
import br.com.product.api.productapi.modules.product.validation.Validation;
import br.com.product.api.productapi.modules.supplier.model.Supplier;
import br.com.product.api.productapi.modules.supplier.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
public class ProductService {

    @Lazy
    @Autowired
    private ProductRepository productRepository;

    @Lazy
    @Autowired
    private SupplierService supplierService;

    @Lazy
    @Autowired
    private CategoryService categoryService;

    @Lazy
    @Autowired
    private List<Validation> validations;

    @Autowired
    private SalesConfirmationSender salesConfirmationSender;

    @Autowired
    private SalesClient salesClient;

    public ProductResponse create(ProductRequest request) {
        validate(request);
        Category category = getCategory(request);
        Supplier supplier = getSupplier(request);
        var entity = productRepository.save(Product.of(request, supplier, category));
        return ProductResponse.of(entity);
    }

    public ProductResponse update(ProductRequest request, Integer id) {
        validate(request);
        validateInformedData(isEmpty(id), "The product's ID must be informed.");
        Category category = getCategory(request);
        Supplier supplier = getSupplier(request);
        var entity = Product.of(request, supplier, category);
        entity.setId(id);
        productRepository.save(entity);
        return ProductResponse.of(entity);
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
        return productRepository.findAll().stream().map(ProductResponse::of).collect(toList());
    }

    public List<ProductResponse> findByName(String name) {
        validateInformedData(isEmpty(name), "The product name must be informed.");
        return productRepository.findByNameIgnoreCaseContaining(name).stream().map(ProductResponse::of).collect(toList());
    }

    public List<ProductResponse> findBySupplierId(Integer supplierId) {
        validateInformedData(isEmpty(supplierId), "The product supplier ID name must be informed.");
        return productRepository.findBySupplierId(supplierId).stream().map(ProductResponse::of).collect(toList());
    }

    public ProductResponse findByIdResponse(Integer id) {
        return ProductResponse.of(findById(id));
    }

    private Product findById(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new ValidationException("There's no product for the given ID."));
    }

    public List<ProductResponse> findByCategoryId(Integer categoryId) {
        validateInformedData(isEmpty(categoryId), "The product category ID name must be informed.");
        return productRepository.findByCategoryId(categoryId).stream().map(ProductResponse::of).collect(toList());
    }

    private void validateInformedData(boolean data, String msg) {
        if(data) throw new ValidationException(msg);
    }

    public boolean existsByCategoryId(Integer categoryId) {
        return productRepository.existsByCategoryId(categoryId);
    }

    public boolean existsBySupplierId(Integer supplierId) {
        return productRepository.existsBySupplierId(supplierId);
    }

    public SuccessResponse delete(Integer id) {
        validateInformedData(isEmpty(id), "The product's ID must be informed.");
        productRepository.deleteById(id);
        return SuccessResponse.create("The product was deleted.");
    }

    public void updateProductStock(ProductStockDTO dto) {
        try {
            validateStockUpdateData(dto);
            updateStock(dto);
        } catch (Exception exception) {
            log.error("Error while trying to update stock for message with error: {}", exception.getMessage(), exception);
            var rejectedMessage = new SalesConfirmationDTO(dto.getSalesId(), SalesStatus.REJECTED);
            salesConfirmationSender.sendConfirmationMessage(rejectedMessage);
        }
    }

    @Transactional
    private void updateStock(ProductStockDTO dto) {
        var productsForUpdate = new ArrayList<Product>();
        dto.getProducts().forEach(salesProduct -> {
            var existingProduct = findById(salesProduct.getProductId());
            validateInformedData(salesProduct.getQuantity() > existingProduct.getQuantityAvailable(), "The product "+existingProduct.getId()+" is out of stock.");
            existingProduct.updateStock(salesProduct.getQuantity());
            productsForUpdate.add(existingProduct);
        });

        if(!isEmpty(productsForUpdate)) {
            productRepository.saveAll(productsForUpdate);
            var approvedMessage = new SalesConfirmationDTO(dto.getSalesId(), SalesStatus.APPROVED);
            salesConfirmationSender.sendConfirmationMessage(approvedMessage);
        }
    }

    private void validateStockUpdateData(ProductStockDTO dto) {
        validateInformedData(isEmpty(dto) || isEmpty(dto.getSalesId()), "The product data and the sales ID must be informed");
        validateInformedData(isEmpty(dto.getProducts()), "The product data or sales ID cannot be null");

        dto.getProducts()
                .forEach(salesProduct ->
                        validateInformedData(isEmpty(salesProduct.getQuantity()) || isEmpty(salesProduct.getProductId()), "The product ID and the quantity must be informed."));
    }

    public ProductSalesResponse findProductSales(Integer id) {
        var entity = findById(id);

        try {
            var sales = salesClient.findSalesByProductId(id)
                    .orElseThrow(() -> new ValidationException("The sales was not found by this product."));
            return ProductSalesResponse.of(entity, sales.getSalesId());
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ValidationException("There was an error trying to get the product's sales.");
        }
    }

    public SuccessResponse checkProductsStock(ProductCheckStockRequest request) {
        validateInformedData(isEmpty(request) || isEmpty(request.getProductsQuantity()), "The request data and product must be informed");
        request.getProductsQuantity().forEach(this::validateCheck);
        return SuccessResponse.create("The stock is ok.");
    }

    private void validateCheck(ProductQuantityDTO dto) {
        validateInformedData(isEmpty(dto.getProductId()) || isEmpty(dto.getQuantity()), "Product ID and quantity must be informed.");
        var entity = findById(dto.getProductId());
        validateInformedData(dto.getQuantity() > entity.getQuantityAvailable(), "The product "+entity.getId()+" is out of stock.");
    }
}
