package org.dsi.ecommerce.controllers.admin;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.dsi.ecommerce.helper.Message;
import org.dsi.ecommerce.helper.dtos.ProductDto;
import org.dsi.ecommerce.helper.converter.DTOConverter;
import org.dsi.ecommerce.models.Category;
import org.dsi.ecommerce.models.Product;
import org.dsi.ecommerce.services.CategoryService;
import org.dsi.ecommerce.services.ProductService;
import org.dsi.ecommerce.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class ProductController {

    public final ProductService productService;
    public final CategoryService categoryService;
    public final UserService userService;
    public final DTOConverter dtoConverter;

    @PostMapping("/addProduct")
    public String addProduct(@Valid  @ModelAttribute Product product,
                             BindingResult result,
                             @RequestParam int categoryId,
                             @RequestParam(name = "image", required = false) MultipartFile file,
                             RedirectAttributes redirectAttributes) throws Exception{
        if(result.hasErrors()) {
            redirectAttributes.addFlashAttribute("message",
                    new Message("Product could not added", "alert-danger", Message.errorMessage(result)));
            return "redirect:/admin/index";
        }

        Category category = categoryService.findByCategoryId(categoryId);
        productService.createProduct(file, product, category);
        redirectAttributes.addFlashAttribute("message",
                new Message("Product added successfully", "alert-success", null));
        return "redirect:/admin/index";
    }


    @GetMapping("product-index/queries")
    public String goProductIndexBySearch(@RequestParam(value = "query", required = false) Optional<String> query,
                                         @RequestParam("productPage") Optional<Integer> productPage,
                                         Model model) {

        int currentProductPage = productPage.orElse(1);
        String myQuery = query.orElse("").trim();

        Page<ProductDto> productDtos = dtoConverter.convertToPageOfProductDTO(
                productService.findProductsBySearchForAdmin(myQuery, currentProductPage)
        );

        System.out.println("HKDSJKDHSJKDHKSJHDKS: "+productDtos);

        model.addAttribute("productDtos", productDtos);
        model.addAttribute("showQuery", true);
        model.addAttribute("myQuery", myQuery);
        model.addAttribute("currentProductPage", currentProductPage);
        model.addAttribute("totalProductPages", productDtos.getTotalPages());

        return "admin/product_index";
    }


    @GetMapping("/product-index")
    public String goProductIndex(@RequestParam("category") Optional<Integer> categoryId,
                                 @RequestParam("productPage") Optional<Integer> productPage,
                                 Model model) {


        int id = categoryId.orElse(1);
        int currentProductPage = productPage.orElse(1);

        Page<ProductDto> productDtos = dtoConverter.convertToPageOfProductDTO(
                        productService.findProductsByCategoryId(id, currentProductPage)
        );

        model.addAttribute("categoryId", id);
        model.addAttribute("productDtos", productDtos);
        model.addAttribute("currentProductPage", currentProductPage);
        model.addAttribute("totalProductPages", productDtos.getTotalPages());
        model.addAttribute("showQuery", false);

        return "admin/product_index";
    }

    @GetMapping("/product-checkout")
    public String goProductCheckout() {
        return "admin/checkout";
    }

    @GetMapping("/product-details")
    public String goProductDetails(@RequestParam("product") Optional<Integer> productId, Model model) throws Exception {
        int id = productId.orElse(1);

        model.addAttribute("productDto",
                dtoConverter.convertToProductDTO(productService.getProductById(id)));

        return "common/product_details";
    }

    @GetMapping("/soft-delete-product")
    public String softDeleteProduct(@RequestParam("product") Optional<Integer> productId) throws Exception {
        int id = productId.orElse(0);
        productService.softDeleteProduct(id);
        return "redirect:/product-details?product="+id;
    }

    @GetMapping("/enable-product")
    public String enableProduct(@RequestParam("product") Optional<Integer> productId) throws Exception {
        int id = productId.orElse(0);
        productService.enableProduct(id);
        return "redirect:/product-details?product="+id;
    }

    @PostMapping("/update-product")
    public String updateProduct(@Valid @ModelAttribute Product product,
                             @RequestParam int categoryId,
                             @RequestParam(name = "image", required = false) MultipartFile file,
                             RedirectAttributes redirectAttributes) throws Exception{

        Category category = categoryService.findByCategoryId(categoryId);
        productService.updateProduct(file, product, category);
        redirectAttributes.addFlashAttribute("message",
                new Message("Product updated successfully", "alert-success", null));
        return "redirect:/product-details?product="+product.getId();
    }
}
