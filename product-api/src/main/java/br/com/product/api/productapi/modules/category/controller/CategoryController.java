package br.com.product.api.productapi.modules.category.controller;

import br.com.product.api.productapi.configuration.exception.SuccessResponse;
import br.com.product.api.productapi.modules.category.dto.CategoryRequest;
import br.com.product.api.productapi.modules.category.dto.CategoryResponse;
import br.com.product.api.productapi.modules.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Lazy
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public CategoryResponse create (@RequestBody CategoryRequest request) {
        return categoryService.create(request);
    }

    @GetMapping
    public List<CategoryResponse> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public CategoryResponse findById(@PathVariable Integer id) {
        return categoryService.findByIdResponse(id);
    }

    @GetMapping("/description/{description}")
    public List<CategoryResponse> findByDescription(@PathVariable String description) {
        return categoryService.findByDescription(description);
    }

    @DeleteMapping("/{id}")
    public SuccessResponse delete(@PathVariable Integer id) {
        return categoryService.delete(id);
    }

    @PutMapping("/{id}")
    public CategoryResponse update(@RequestBody CategoryRequest request, @PathVariable Integer id) {
        return categoryService.update(request, id);
    }
}
