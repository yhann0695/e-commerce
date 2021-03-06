package br.com.product.api.productapi.modules.product.repository;

import br.com.product.api.productapi.modules.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByNameIgnoreCaseContaining(String name);

    List<Product> findBySupplierId(Integer id);

    List<Product> findByCategoryId(Integer id);

    Boolean existsByCategoryId(Integer categoryId);

    Boolean existsBySupplierId(Integer categoryId);
}
