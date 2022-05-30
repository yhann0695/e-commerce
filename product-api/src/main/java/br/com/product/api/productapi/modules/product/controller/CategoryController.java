package br.com.product.api.productapi.modules.product.controller;

import br.com.product.api.productapi.modules.product.dto.category.CategoryRequest;
import br.com.product.api.productapi.modules.product.dto.category.CategoryResponse;
import br.com.product.api.productapi.modules.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public CategoryResponse create (@RequestBody CategoryRequest request) {
        return categoryService.create(request);
    }
}
