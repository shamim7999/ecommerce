package org.dsi.ecommerce.controllers.admin;

import org.dsi.ecommerce.models.Category;
import org.dsi.ecommerce.models.Product;
import org.dsi.ecommerce.services.CategoryService;
import org.dsi.ecommerce.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AddProductController {

    public final ProductService productService;
    public final CategoryService categoryService;

    public AddProductController(ProductService productService,
                                CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute Product product,
                             @ModelAttribute Category category,
                             RedirectAttributes redirectAttributes) throws Exception{
        productService.createProduct(product, category);
        redirectAttributes.addFlashAttribute("message", "Product Added Successfully!!");
        redirectAttributes.addFlashAttribute("type", "alert-success");
        return "redirect:/admin/index";
    }
}
