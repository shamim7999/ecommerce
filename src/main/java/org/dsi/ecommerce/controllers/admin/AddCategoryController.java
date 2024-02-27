package org.dsi.ecommerce.controllers.admin;

import org.dsi.ecommerce.models.Category;
import org.dsi.ecommerce.services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AddCategoryController {

    private final CategoryService categoryService;

    public AddCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/addCategory")
    public String addCategory(@ModelAttribute Category category, RedirectAttributes redirectAttributes) throws Exception{
        categoryService.createCategory(category);
        redirectAttributes.addFlashAttribute("message", "Category Created Successfully!!");
        redirectAttributes.addFlashAttribute("type", "alert-success");
        return "redirect:/admin/index";
    }

}
