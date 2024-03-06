package org.dsi.ecommerce.repositories;

import org.dsi.ecommerce.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findAllByCategory_IdAndStatus(int categoryId, boolean status, Pageable pageable);

    Page<Product> findAllByCategory_Id(int categoryId, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.status = false WHERE p.id = :productId")
    public void softDelete(int productId);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.status = true WHERE p.id = :productId")
    public void enableProduct(int productId);
}
