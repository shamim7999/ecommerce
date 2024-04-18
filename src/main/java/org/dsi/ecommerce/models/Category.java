package org.dsi.ecommerce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "")
    @NotNull
    @Column(length = 50, nullable = false)
    @Size(min = 10, max = 50, message = "Title should be 10 to 50 characters.")
    private String title;

    @NotBlank(message = "")
    @NotNull
    @Column(length = 500, nullable = false)
    @Size(min = 20, max = 500, message = "Description should be 20 to 500 characters.")
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();
}
