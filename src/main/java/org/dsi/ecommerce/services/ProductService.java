package org.dsi.ecommerce.services;

import org.dsi.ecommerce.models.Product;
import org.dsi.ecommerce.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    public final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(Product product) throws Exception {
        product.setPhoto("default.png");
        System.out.println("----------------------\n"+product.getName()+"-----------------------");
        productRepository.save(product);
    }
}
