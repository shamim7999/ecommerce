package org.dsi.ecommerce.services;

import org.dsi.ecommerce.helper.ImageUpload;
import org.dsi.ecommerce.helper.ShorterSentence;
import org.dsi.ecommerce.models.Category;
import org.dsi.ecommerce.models.Product;
import org.dsi.ecommerce.repositories.CategoryRepository;
import org.dsi.ecommerce.repositories.ProductRepository;
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

    public List<Product> findAllProducts() {
        return productRepository.findAll()
                .stream()
                .peek(product -> product.setDescription(ShorterSentence.get10Words(product.getDescription())))
                .collect(Collectors.toList());
    }
}
