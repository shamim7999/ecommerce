package org.dsi.ecommerce.repositories;

import org.dsi.ecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByCategory_Id(int categoryId);
}
