package org.dsi.ecommerce.helper.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;

    private String name;

    private String email;

    private String password;

    private String phone;

    private String pic;

    private String role;

    private String address;

    private boolean isEnabled;
}

