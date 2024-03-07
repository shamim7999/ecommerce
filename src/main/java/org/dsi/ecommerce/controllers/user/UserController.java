package org.dsi.ecommerce.controllers.user;

import org.dsi.ecommerce.helper.UserDto;
import org.dsi.ecommerce.helper.converter.DTOConverter;
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
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final CategoryService categoryService;

    private final ProductService productService;

    private final DTOConverter dtoConverter;

    public UserController(UserService userService, CategoryService categoryService,
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
        return "user/home";
    }

    @GetMapping("/product-checkout")
    public String goProductCheckout() {
        return "user/checkout";
    }

    @GetMapping("/user-lists")
    public String goUserLists(Model model) {
        List<UserDto> userDtos = dtoConverter.convertToListOfUserDTO(userService.getAllUsers());
        model.addAttribute("userDtos", userDtos);
        return "user/user_panel";
    }
}
