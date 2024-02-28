package org.dsi.ecommerce.helper.converter;

import org.dsi.ecommerce.helper.CategoryDto;
import org.dsi.ecommerce.helper.ProductDto;
import org.dsi.ecommerce.models.Category;
import org.dsi.ecommerce.models.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DTOConverter {
    private final ModelMapper modelMapper;

    public DTOConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public ProductDto convertToProductDTO(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        productDto.setCategoryDto(modelMapper.map(product.getCategory(), CategoryDto.class));
        return productDto;
    }

    public List<ProductDto> convertToListOfProductDTO(List<Product> products) {
        return products.stream()
                .map(this::convertToProductDTO)
                .collect(Collectors.toList());
    }

    public CategoryDto convertToCategoryDTO(Category category) {
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        categoryDto.setProductDtos(
                category.getProducts().stream()
                        .map(this::convertToProductDTO)
                        .collect(Collectors.toList())
        );
        return categoryDto;
    }

    public List<CategoryDto> convertToListOfCategoryDTO(List<Category> categories) {
        return categories.stream()
                .map(this::convertToCategoryDTO)
                .collect(Collectors.toList());
    }
}
