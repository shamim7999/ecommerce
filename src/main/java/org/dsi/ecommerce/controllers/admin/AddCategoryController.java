package org.dsi.ecommerce.controllers.admin;

import org.dsi.ecommerce.helper.UserDto;
import org.dsi.ecommerce.models.Category;
import org.dsi.ecommerce.services.CategoryService;
import org.dsi.ecommerce.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AddCategoryController {

    private final CategoryService categoryService;
    private final UserService userService;

    public AddCategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @ModelAttribute
    public Principal sendPrincipal(Principal principal) {
        return principal;
    }

    @ModelAttribute
    public UserDto sendUserDetails(Principal principal) {
        return userService.getUserDetails(principal);
    }

    @PostMapping("/addCategory")
    public String addCategory(@ModelAttribute Category category, RedirectAttributes redirectAttributes) {
        categoryService.createCategory(category);
        redirectAttributes.addFlashAttribute("message", "Category Created Successfully!!");
        redirectAttributes.addFlashAttribute("type", "alert-success");
        return "redirect:/admin/index";
    }

}
