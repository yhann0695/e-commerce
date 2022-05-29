package br.com.product.api.productapi.modules.product.model;

import javax.persistence.*;

@Entity
@Table(name = "CATEGORY")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "CATEGORY_DESCRIPTION", nullable = false)
    private String description;
}
