package org.dsi.ecommerce.repositories;

import org.dsi.ecommerce.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findAllByCategory_IdAndStatus(int categoryId, boolean status, Pageable pageable);

    Page<Product> findAllByCategory_Id(int categoryId, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE (LOWER(p.name)) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Product> findProductsBySearchForAdmin(String query, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE (LOWER(p.name)) LIKE LOWER(CONCAT('%', :query, '%')) AND p.status=true")
    Page<Product> findProductsBySearchForUser(String query, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.status = false WHERE p.id = :productId")
    public void softDelete(int productId);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.status = true WHERE p.id = :productId")
    public void enableProduct(int productId);

    List<Product> findAllByStatusTrue(Sort sort);
}
