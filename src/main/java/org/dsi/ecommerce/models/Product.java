package org.dsi.ecommerce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @NotBlank(message = "")
    @NotNull
    @Column(length = 200, nullable = false)
    @Size(min = 10, max = 200, message = "Title should be 10 to 200 characters.")
    private String name;


    @NotBlank(message = "")
    @NotNull
    @Column(length = 1000, nullable = false)
    @Size(min = 20, max = 1000, message = "Description should be 20 to 1000 characters.")
    private String description;


    @Column(length = 200, nullable = false)
    private String photo;


    @NotNull(message = "Price field should not be null.")
    @Column(nullable = false)
    private Integer price;


    @NotNull(message = "Discount field should not be null")
    @Column(nullable = false)
    private Integer discount;


    private Boolean status=true;


    @NotNull(message = "Quantity field should not be null")
    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    private Category category;
}
