package org.dsi.ecommerce.controllers.admin;

import org.dsi.ecommerce.helper.CategoryDto;
import org.dsi.ecommerce.helper.ProductDto;
import org.dsi.ecommerce.helper.UserDto;
import org.dsi.ecommerce.helper.converter.DTOConverter;
import org.dsi.ecommerce.models.Product;
import org.dsi.ecommerce.services.CategoryService;
import org.dsi.ecommerce.services.ProductService;
import org.dsi.ecommerce.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final CategoryService categoryService;

    private final ProductService productService;

    private final DTOConverter dtoConverter;

    public AdminController(UserService userService, CategoryService categoryService,
                           ProductService productService, DTOConverter dtoConverter) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.dtoConverter = dtoConverter;
    }

    @ModelAttribute
    public Principal sendPrincipal(Principal principal) {
        return principal;
    }

    @ModelAttribute
    public UserDto sendUserDetails(Principal principal) {
        return userService.getUserDetails(principal);
    }

    @GetMapping({"/", "/index"})
    public String goHome(Model model) {

        List<UserDto> userDtos = dtoConverter.convertToListOfUserDTO(userService.getAllUsers());

        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("products", productService.getAllProductsSortedByCategory());
        model.addAttribute("userDtos", userDtos);
        return "admin/home";
    }
}
