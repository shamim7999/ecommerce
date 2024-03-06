package org.dsi.ecommerce.helper.converter;

import org.dsi.ecommerce.helper.CategoryDto;
import org.dsi.ecommerce.helper.ProductDto;
import org.dsi.ecommerce.helper.UserDto;
import org.dsi.ecommerce.models.Category;
import org.dsi.ecommerce.models.Product;
import org.dsi.ecommerce.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DTOConverter {
    private final ModelMapper modelMapper;

    public DTOConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDto convertToUserDTO(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public List<UserDto> convertToListOfUserDTO(List<User> users) {
        return users.stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
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

    public Page<ProductDto> convertToPageOfProductDTO(Page<Product> productPage) {
        List<ProductDto> productDtos = productPage.getContent().stream()
                .map(this::convertToProductDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(productDtos, productPage.getPageable(), productPage.getTotalElements());
    }

    public Page<CategoryDto> convertToPageOfCategoryDTO(Page<Category> categoryPage) {
        List<CategoryDto> categoryDtos = categoryPage.getContent().stream()
                .map(this::convertToCategoryDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(categoryDtos, categoryPage.getPageable(), categoryPage.getTotalElements());
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
