package br.com.product.api.productapi.modules.supplier.model;

import javax.persistence.*;

@Entity
@Table(name = "SUPPLIER")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "SUPPLIER_NAME", nullable = false)
    private String name;
}
