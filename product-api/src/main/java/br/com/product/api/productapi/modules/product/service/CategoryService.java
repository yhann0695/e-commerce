package br.com.product.api.productapi.modules.product.service;

import br.com.product.api.productapi.modules.product.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;
}
