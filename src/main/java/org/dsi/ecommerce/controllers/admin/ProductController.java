package org.dsi.ecommerce.controllers.admin;

import jakarta.validation.Valid;
import org.dsi.ecommerce.helper.CategoryDto;
import org.dsi.ecommerce.helper.ProductDto;
import org.dsi.ecommerce.helper.UserDto;
import org.dsi.ecommerce.helper.converter.DTOConverter;
import org.dsi.ecommerce.models.Category;
import org.dsi.ecommerce.models.Product;
import org.dsi.ecommerce.services.CategoryService;
import org.dsi.ecommerce.services.ProductService;
import org.dsi.ecommerce.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class ProductController {

    public final ProductService productService;
    public final CategoryService categoryService;
    public final UserService userService;
    public final DTOConverter dtoConverter;

    public ProductController(ProductService productService,
                             CategoryService categoryService,
                             UserService userService,
                             DTOConverter dtoConverter) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.dtoConverter = dtoConverter;
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

    @PostMapping("/addProduct")
    public String addProduct(@Valid  @ModelAttribute Product product,
                             @RequestParam int categoryId,
                             @RequestParam(name = "image", required = false) MultipartFile file,
                             RedirectAttributes redirectAttributes) throws Exception{
        Category category = categoryService.findByCategoryId(categoryId);
        productService.createProduct(file, product, category);
        redirectAttributes.addFlashAttribute("message", "Product Added Successfully!!");
        redirectAttributes.addFlashAttribute("type", "alert-success");
        return "redirect:/admin/index";
    }

    @GetMapping("/product-index")
    public String goProductIndex(@RequestParam("category") Optional<Integer> categoryId, Model model) {

        int id = categoryId.orElse(1);

        List<ProductDto> productDtos =
                dtoConverter.convertToListOfProductDTO(productService.findProductsByCategoryId(id));
        List<CategoryDto> categoryDtos =
                dtoConverter.convertToListOfCategoryDTO(categoryService.getAllCategories());
        model.addAttribute("productDtos", productDtos);
        model.addAttribute("categoryDtos", categoryDtos);
        return "admin/product_index";
    }

    @GetMapping("/product-checkout")
    public String goProductCheckout() {
        return "admin/checkout";
    }
}
