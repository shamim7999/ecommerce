package org.dsi.ecommerce.controllers.admin;

import org.dsi.ecommerce.models.Product;
import org.dsi.ecommerce.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AddProductController {

    public final ProductService productService;

    public AddProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute Product product, RedirectAttributes redirectAttributes) throws Exception{
        productService.createProduct(product);
        redirectAttributes.addFlashAttribute("message", "Product Added Successfully!!");
        redirectAttributes.addFlashAttribute("type", "alert-success");
        return "redirect:/admin/index";
    }
}
