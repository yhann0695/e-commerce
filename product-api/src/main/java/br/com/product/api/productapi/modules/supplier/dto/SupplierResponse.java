package br.com.product.api.productapi.modules.supplier.dto;

import br.com.product.api.productapi.modules.supplier.model.Supplier;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class SupplierResponse {

    private Integer id;
    private String name;

    public static SupplierResponse of(Supplier entity) {
        var response = new SupplierResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }
}
