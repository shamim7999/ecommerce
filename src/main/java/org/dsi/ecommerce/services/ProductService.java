package org.dsi.ecommerce.services;

import org.dsi.ecommerce.models.Category;
import org.dsi.ecommerce.models.Product;
import org.dsi.ecommerce.repositories.CategoryRepository;
import org.dsi.ecommerce.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    public final ProductRepository productRepository;
    public final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public void createProduct(Product product, Category category) throws Exception {
        product.setPhoto("default.png");
        //System.out.println("----------------------\n"+product.getName()+"-----------------------");

        product.setCategory(category);
        category.getProducts().add(product);

        System.out.println("SUCCESSSSSSS");

        categoryRepository.save(category);

    }
}
