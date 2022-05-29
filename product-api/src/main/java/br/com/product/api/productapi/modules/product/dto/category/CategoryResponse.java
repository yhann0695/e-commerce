package br.com.product.api.productapi.modules.product.dto.category;

import br.com.product.api.productapi.modules.product.model.Category;
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
