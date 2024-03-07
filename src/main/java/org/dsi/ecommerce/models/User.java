package org.dsi.ecommerce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dsi.ecommerce.helper.validators.ValidEmail;

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



    @NotBlank(message = "Name cannot be Blank.")
    @NotNull(message = "Name cannot be Null.")
    @Column(length = 30, nullable = false)
    @Size(min = 3, max = 20, message = "Enter name between 3 to 20 characters.")
    private String name;



    @ValidEmail
    @NotBlank(message = "Email cannot be Blank.")
    @NotNull(message = "Email cannot be Null.")
    @Column(length = 50, nullable = false, unique = true)
    private String email;



    @NotBlank(message = "Password cannot be Blank.")
    @NotNull(message = "Password cannot be Null.")
    @Column(length = 200, nullable = false)
    @Size(min = 3, message = "Password must be at least 3 characters long")
    private String password;



    @NotBlank(message = "Phone field cannot be Blank.")
    @NotNull(message = "Phone field cannot be Null.")
    @Column(length = 30, nullable = false)
    private String phone;



    private String pic;

    private String role;



    @NotBlank
    @NotNull
    @Column(length = 100, nullable = false)
    private String address;



    private boolean isEnabled;
}
