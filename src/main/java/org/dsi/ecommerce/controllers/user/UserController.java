package org.dsi.ecommerce.controllers.user;

import org.dsi.ecommerce.helper.UserDto;
import org.dsi.ecommerce.services.CategoryService;
import org.dsi.ecommerce.services.ProductService;
import org.dsi.ecommerce.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final CategoryService categoryService;

    private final ProductService productService;

    public UserController(UserService userService, CategoryService categoryService, ProductService productService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
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
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("products", productService.getAllProductsSortedByCategory());
        return "user/home";
    }

    @GetMapping("/product-checkout")
    public String goProductCheckout() {
        return "user/checkout";
    }
}
