package org.dsi.ecommerce.controllers.user;

import lombok.AllArgsConstructor;
import org.dsi.ecommerce.helper.converter.DTOConverter;
import org.dsi.ecommerce.services.CategoryService;
import org.dsi.ecommerce.services.ProductService;
import org.dsi.ecommerce.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final DTOConverter dtoConverter;

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

    @GetMapping("/user-lists")
    public String goUserLists(Model model) {
        return "user/user_panel";
    }
}
