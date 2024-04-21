package org.dsi.ecommerce.controllers.global;

import lombok.AllArgsConstructor;
import org.dsi.ecommerce.helper.dtos.CategoryDto;
import org.dsi.ecommerce.helper.dtos.UserDto;
import org.dsi.ecommerce.helper.converter.DTOConverter;
import org.dsi.ecommerce.services.CategoryService;
import org.dsi.ecommerce.services.UserService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.List;

@ControllerAdvice
@AllArgsConstructor
public class GlobalPayload {
    private final UserService userService;
    private final CategoryService categoryService;
    private final DTOConverter dtoConverter;

    @ModelAttribute
    public Principal sendPrincipal(Principal principal) {
        return principal;
    }

    @ModelAttribute
    public UserDto sendUserDetails(Principal principal) {
        return userService.getUserDetails(principal);
    }

    @ModelAttribute("categoryDtos")
    public List<CategoryDto> sendCategoryDtos() {
        return dtoConverter.convertToListOfCategoryDTO(categoryService.getAllCategories());
    }

    @ModelAttribute("userDtos")
    public List<UserDto> sendUserDtos() {
        return dtoConverter.convertToListOfUserDTO(userService.getAllUsers());
    }

}
