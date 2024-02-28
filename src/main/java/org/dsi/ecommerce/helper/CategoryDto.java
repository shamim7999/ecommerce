package org.dsi.ecommerce.helper;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CategoryDto {
    private int id;

    private String title;

    private String description;

    private List<ProductDto> productDtos = new ArrayList<>();
}
