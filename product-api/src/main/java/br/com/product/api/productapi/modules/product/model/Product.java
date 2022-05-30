package br.com.product.api.productapi.modules.product.model;

import br.com.product.api.productapi.modules.category.model.Category;
import br.com.product.api.productapi.modules.supplier.model.Supplier;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCT")
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
}
