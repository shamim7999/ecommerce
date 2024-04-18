package org.dsi.ecommerce.controllers.admin;

import jakarta.validation.Valid;
import org.dsi.ecommerce.helper.Message;
import org.dsi.ecommerce.helper.UserDto;
import org.dsi.ecommerce.models.Category;
import org.dsi.ecommerce.services.CategoryService;
import org.dsi.ecommerce.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;

    public CategoryController(CategoryService categoryService, UserService userService) {
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

    @PostMapping("/add-category")
    public String addCategory(@Valid @ModelAttribute Category category,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            System.out.println("ERROR: "+result.getAllErrors());
            redirectAttributes.addFlashAttribute("message",
                    new Message("Category could not added", "alert-danger", Message.errorMessage(result)));
            return "redirect:/admin/index";
        }

        categoryService.createCategory(category);
        redirectAttributes.addFlashAttribute("message",
                new Message("Category added successfully", "alert-success", null));

        return "redirect:/admin/index";
    }

}
