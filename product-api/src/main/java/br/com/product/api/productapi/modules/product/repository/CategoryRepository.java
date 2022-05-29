package br.com.product.api.productapi.modules.product.repository;

import br.com.product.api.productapi.modules.product.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
