package org.dsi.ecommerce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @NotNull
    @Column(length = 30, nullable = false)
    private String name;

    @NotBlank
    @NotNull
    @Column(length = 1000, nullable = false)
    private String description;

    @NotBlank
    @NotNull
    @Column(length = 30, nullable = false)
    private String photo;

    @NotBlank
    @NotNull
    @Column(nullable = false)
    private int price;

    @NotBlank
    @NotNull
    @Column(nullable = false)
    private int discount;

    @NotBlank
    @NotNull
    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    private Category category;
}
