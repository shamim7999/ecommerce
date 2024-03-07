package org.dsi.ecommerce.services;

import org.dsi.ecommerce.helper.ImageUpload;
import org.dsi.ecommerce.helper.ShorterSentence;
import org.dsi.ecommerce.models.Category;
import org.dsi.ecommerce.models.Product;
import org.dsi.ecommerce.repositories.CategoryRepository;
import org.dsi.ecommerce.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    public final ProductRepository productRepository;
    public final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public void createProduct(MultipartFile file, Product product, Category category) throws IOException {
        product.setPhoto("default.png");
        if(file != null && !file.isEmpty()) {
            product.setPhoto(file.getOriginalFilename());
            ImageUpload.uploadImage(file);
        }
        product.setCategory(category);
        productRepository.save(product);
    }

    public void updateProduct(MultipartFile file, Product product, Category category) throws IOException {
        if(file != null && !file.isEmpty()) {
            product.setPhoto(file.getOriginalFilename());
            ImageUpload.uploadImage(file);
        }
        product.setCategory(category);
        productRepository.save(product);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll()
                .stream()
                .peek(product -> product.setDescription(ShorterSentence.get10Words(product.getDescription())))
                .collect(Collectors.toList());
    }

    public Page<Product> findProductsByCategoryId(int categoryId, int currentPage) {
        Pageable pageable = PageRequest.of(currentPage-1, 3);
        return productRepository.findAllByCategory_Id(categoryId,pageable)
                .map(product -> {
                    product.setDescription(ShorterSentence.get10Words(product.getDescription()));
                    return product;
                });
    }

    public Page<Product> findProductsByStatusSetToTrue(int categoryId, int currentPage) {
        Pageable pageable = PageRequest.of(currentPage-1, 3);
        return productRepository.findAllByCategory_IdAndStatus(categoryId, true, pageable)
                .map(product -> {
                    product.setDescription(ShorterSentence.get10Words(product.getDescription()));
                    return product;
                });
    }

    public List<Product> getAllProductsSortedByCategory() {
        return productRepository.findAll(Sort.by("category_id"));
    }

    public Product getProductById(int id) throws Exception {
        return productRepository.findById(id)
                .orElseThrow(() -> new Exception("Product with ID " + id + " not found"));
    }

    public void softDeleteProduct(int productId) throws Exception {
        productRepository.softDelete(productId);
    }

    public void enableProduct(int productId) throws Exception {
        productRepository.enableProduct(productId);
    }
}
