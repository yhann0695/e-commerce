package br.com.product.api.productapi.modules.supplier.model;

import br.com.product.api.productapi.modules.supplier.dto.SupplierRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Entity
@Table(name = "SUPPLIER")
@Setter
@Getter
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "SUPPLIER_NAME", nullable = false)
    private String name;

    public static Supplier of(SupplierRequest request) {
        var entity = new Supplier();
        BeanUtils.copyProperties(request, entity);
        return entity;
    }
}
