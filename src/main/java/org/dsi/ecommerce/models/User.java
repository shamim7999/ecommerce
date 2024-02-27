package org.dsi.ecommerce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @NotNull
    @Column(length = 30, nullable = false)
    private String name;

    @Email
    @NotBlank
    @NotNull
    @Column(length = 50, nullable = false)
    private String email;

    @NotBlank
    @NotNull
    @Column(length = 200, nullable = false)
    private String password;

    @NotBlank
    @NotNull
    @Column(length = 30, nullable = false)
    private String phone;

    @NotBlank
    @NotNull
    @Column(length = 100, nullable = false)
    private String pic;

    @NotBlank
    @NotNull
    @Column(length = 12, nullable = false)
    private String role;

    @NotBlank
    @NotNull
    @Column(length = 100, nullable = false)
    private String address;

    private boolean isEnabled;
}
