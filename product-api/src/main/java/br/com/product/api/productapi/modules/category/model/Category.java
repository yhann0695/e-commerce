package br.com.product.api.productapi.modules.category.model;

import br.com.product.api.productapi.modules.category.dto.CategoryRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Entity
@Table(name = "CATEGORY")
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "CATEGORY_DESCRIPTION", nullable = false)
    private String description;

    public static Category of(CategoryRequest request) {
        var entity = new Category();
        BeanUtils.copyProperties(request, entity);
        return entity;
    }
}
