package org.dsi.ecommerce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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

    @NotBlank
    @NotNull
    @Column(length = 200, nullable = false)
    private String name;

    @NotBlank
    @NotNull
    @Column(length = 1000, nullable = false)
    private String description;

    @Column(length = 200, nullable = false)
    private String photo;

    @NotNull
    @Column(nullable = false)
    private int price;

    @NotNull
    @Column(nullable = false)
    private int discount;

    private boolean status=true;

    @NotNull
    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    private Category category;
}
