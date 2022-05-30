package br.com.product.api.productapi.modules.product.dto;

import br.com.product.api.productapi.modules.category.dto.CategoryResponse;
import br.com.product.api.productapi.modules.product.model.Product;
import br.com.product.api.productapi.modules.supplier.dto.SupplierResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Integer id;
    private String name;
    private Integer quantityAvailable;
    @JsonProperty("created_it")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    private SupplierResponse supplier;
    private CategoryResponse category;

    public static ProductResponse of(Product entity) {
        return ProductResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .quantityAvailable(entity.getQuantityAvailable())
                .createdAt(entity.getCreatedAt())
                .supplier(SupplierResponse.of(entity.getSupplier()))
                .category(CategoryResponse.of(entity.getCategory()))
                .build();
    }
}
