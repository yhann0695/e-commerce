package br.com.product.api.productapi.modules.category.dto;

import br.com.product.api.productapi.modules.category.model.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

public class CategoryResponse {

    @Setter
    @Getter
    private Integer id;

    @Setter
    @Getter
    private String description;

    public static CategoryResponse of(Category category) {
        var response = new CategoryResponse();
        BeanUtils.copyProperties(category, response);
        return response;
    }
}
