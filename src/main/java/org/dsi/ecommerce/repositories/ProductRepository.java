package org.dsi.ecommerce.repositories;

import org.dsi.ecommerce.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findAllByCategory_Id(int categoryId, Pageable pageable);
}
