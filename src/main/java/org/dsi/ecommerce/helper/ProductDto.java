package org.dsi.ecommerce.helper;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {

    private int id;


    private String name;


    private String description;


    private String photo;


    private int price;


    private int discount;


    private int quantity;


    private CategoryDto categoryDto;

    public int discountedPrice() {
        int newPrice = (int)(this.price * this.discount)/100;
        return (this.price - newPrice);
    }
}
