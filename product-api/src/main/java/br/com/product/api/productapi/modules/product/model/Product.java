package br.com.product.api.productapi.modules.product.model;

import br.com.product.api.productapi.modules.category.model.Category;
import br.com.product.api.productapi.modules.product.dto.ProductRequest;
import br.com.product.api.productapi.modules.supplier.model.Supplier;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRODUCT")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "PRODUCT_NAME", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "FK_CATEGORY", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "FK_SUPPLIER", nullable = false)
    private Supplier supplier;

    @Column(name = "PRODUCT_QUANTITY_AVAILABLE", nullable = false)
    private Integer quantityAvailable;

    @Column(name = "PRODUCT_CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public static Product of(ProductRequest request, Supplier supplier, Category category) {
        return Product.builder()
                .name(request.getName())
                .quantityAvailable(request.getQuantityAvailable())
                .supplier(supplier)
                .category(category)
                .build();
    }

    public void updateStock(Integer quantity) {
        quantityAvailable = quantityAvailable - quantity;
    }
}
